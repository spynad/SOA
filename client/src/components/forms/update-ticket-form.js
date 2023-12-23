import {useSnackbar} from "notistack";
import {useForm} from "antd/es/form/Form";
import {useState} from "react";
import {TICKETS_API, xml_axios} from "../../utils/api";
import {Button, Form} from "antd";
import {InputNumber} from "antd/es";
import TicketForm from "./ticket-form";

export function UpdateTicketForm(){
    const {enqueueSnackbar, closeSnackbar} = useSnackbar();

    const [updateForm] = useForm();
    const [isUpdateTicketModalOpen, setIsUpdateTicketModalOpen] = useState(false);
    const [initialValues, setInitialValues] = useState(undefined);
    const [ticketId, setTicketId] = useState(undefined);
    const [creationDate, setCreationDate] = useState(undefined);

    const showUpdateFlatModal = (e) => {
        xml_axios.get(`${TICKETS_API}/${e["id"]}`)
            .then((response) => {
                if(response.data.getTicketByIdResponse.return[0]["code"][0] === '404') {
                    let msg = "Билет с заданным id не найден"
                    enqueueSnackbar(msg, {
                        autoHideDuration: 5000,
                        variant: "error"
                    })
                } else {
                    let data = undefined
                    if (response.data) {
                        data = response.data.getTicketByIdResponse.return[0].result[0]
                    }
                    setInitialValues(data)
                    setIsUpdateTicketModalOpen(true)
                    setTicketId(e["id"])
                    setCreationDate(data["creationDate"])
                }
            })
    }

    const handleFormSubmit = (body) => {
        if (ticketId) {
            body['id'] = ticketId
            body['creationDate'] = creationDate
            xml_axios.put(`${TICKETS_API}`, {'element': body})
                .then((response) => {
                    enqueueSnackbar("Успешно обновлен билет с id: " + ticketId, {
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
                        Обновить билет
                    </Button>
                </Form.Item>
            </Form>
            <TicketForm formVisible={isUpdateTicketModalOpen && initialValues !== undefined}
                      onCancel={handleUpdateTicketCancel}
                      onFinish={handleFormSubmit}
                      title={initialValues ? `Обновить билет с ID: ${ticketId}` : "Добавить билет"}
                      initialValues={initialValues}
            />
        </>
    )
}