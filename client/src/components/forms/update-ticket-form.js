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
                let data = undefined
                if (response.data){
                    data = response.data["ticket"]
                }
                if (data === undefined){
                    enqueueSnackbar("Билет не найден!", {
                        autoHideDuration: 2000,
                        variant: "error"
                    })
                }
                setInitialValues(data)
                setIsUpdateTicketModalOpen(true)
                setTicketId(e["id"])
                setCreationDate(data["creationDate"])
            })
            .catch((err) => {
                let error = err.response.data
                let msg = error.message
                if(err.response.status == 404) {
                   msg = "Билет с заданным id не найден"
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
            body['creationDate'] = creationDate
            xml_axios.put(`${TICKETS_API}`, {'ticket': body})
                .then((response) => {
                    const ticket = response.data
                    enqueueSnackbar("Успешно обновлен билет с id: " + ticket.id, {
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