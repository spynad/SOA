import {useSnackbar} from "notistack";
import {useForm} from "antd/es/form/Form";
import {useState} from "react";
import {PERSON_API, xml_axios} from "../../utils/api";
import {Button, Form} from "antd";
import {InputNumber} from "antd/es";
import PersonForm from "./person-form";

export function UpdatePersonForm(){
    const {enqueueSnackbar, closeSnackbar} = useSnackbar();

    const [updateForm] = useForm();
    const [isUpdateTicketModalOpen, setIsUpdateTicketModalOpen] = useState(false);
    const [initialValues, setInitialValues] = useState(undefined);
    const [ticketId, setTicketId] = useState(undefined);
    const [creationDate] = useState(undefined);

    const showUpdateFlatModal = (e) => {
        xml_axios.get(`${PERSON_API}/${e["id"]}`)
            .then((response) => {
                if(response.data.getPersonByIdResponse.return[0]["status"][0] === '404') {
                    let msg = "Человек с заданным id не найден"
                    enqueueSnackbar(msg, {
                        autoHideDuration: 5000,
                        variant: "error"
                    })
                } else {
                    let data = undefined
                    if (response.data) {
                        data = response.data.getPersonByIdResponse.return[0].result[0]
                    }
                    setInitialValues(data)
                    setIsUpdateTicketModalOpen(true)
                    setTicketId(e["id"])
                }
            })
            .catch((err) => {
                let error = err.response.data
                let msg = error.message
                if(err.response.status == 404) {
                   msg = "Человек с заданным id не найден"
                }
                enqueueSnackbar(msg, {
                    autoHideDuration: 5000,
                    variant: "error"
                })
            })
    }

    const handleFormSubmit = (body) => {
        if (ticketId) {
            body['id'] = ticketId
            xml_axios.put(`${PERSON_API}`, {'element': body})
                .then((response) => {
                    enqueueSnackbar("Успешно обновлен человек с id: " + ticketId, {
                        autoHideDuration: 5000,
                        variant: "success"
                    })
                    setIsUpdateTicketModalOpen(false)
                })
        }
    }

    const handleUpdateTicketCancel = () => {
        setIsUpdateTicketModalOpen(false)
    }

    return (
        <>
            <Form form={updateForm}
                  onFinish={showUpdateFlatModal}
                  labelCol={{span: 8}}
                  wrapperCol={{span: 16}}
                  layout={"inline"}
            >
                <Form.Item label={"ID"}
                           name={"id"}
                           rules={[
                               {required: true, message: "Пожалуйста введите ID!"},
                               () => ({
                                   validator(_, value) {
                                       if (Number.isInteger(Number(value)) && value > 0) {
                                           return Promise.resolve();
                                       }
                                       return Promise.reject(new Error('ID должен быть больше 0!'));
                                   },
                               }),
                           ]}
                           style={{width: 200}}
                >
                    <InputNumber/>
                </Form.Item>
                <Form.Item>
                    <Button type={"primary"} onClick={updateForm.submit} style={{width: 150}}>
                        Обновить человека
                    </Button>
                </Form.Item>
            </Form>
            <PersonForm formVisible={isUpdateTicketModalOpen && initialValues !== undefined}
                      onCancel={handleUpdateTicketCancel}
                      onFinish={handleFormSubmit}
                      title={initialValues ? `Обновить человека с ID: ${ticketId}` : "Добавить человека"}
                      initialValues={initialValues}
            />
        </>
    )
}