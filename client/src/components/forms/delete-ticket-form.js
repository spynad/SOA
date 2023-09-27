import {useSnackbar} from "notistack";
import {useForm} from "antd/es/form/Form";
import axios from "axios";
import {TICKETS_API} from "../../utils/api";
import {Button, Form} from "antd";
import {InputNumber} from "antd/es";

export function DeleteTicketForm(){
    const {enqueueSnackbar, closeSnackbar} = useSnackbar();
    const [deleteForm] = useForm();

    const handleTicketDelete = (e) => {
        axios.delete(`${TICKETS_API}/${e['id']}`)
            .then((response) => {
                    enqueueSnackbar(`Успешно удален билет с id ${e['id']}`, {
                        autoHideDuration: 5000,
                        variant: "success"
                    })
                }
            )
            .catch((err) => {
                let error = err.response.data
                enqueueSnackbar(error.message, {
                    autoHideDuration: 5000,
                    variant: "error"
                })
            })
    }

    return (
        <>
            <Form form={deleteForm}
                  onFinish={handleTicketDelete}
                  layout={"inline"}
                  labelCol={{span: 8}}
                  wrapperCol={{span: 16}}
            >
                <Form.Item label={"ID"}
                           name={"id"}
                           rules={[
                               {required: true, message: 'Пожалуйста введите ID!'},
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
                    <Button type={"primary"} onClick={deleteForm.submit} style={{width: 150}}>
                        Удалить билет
                    </Button>
                </Form.Item>
            </Form>
        </>
    )
}