import {useForm} from "antd/es/form/Form";
import {useSnackbar} from "notistack";
import {useState} from "react";
import {xml_axios, SELL_TICKET} from "../../../utils/api";
import {Button, Form, InputNumber} from "antd";
import {OperationResponseModal} from "../templates/operation-response-modal";

export function SellTicket() {
    const [form] = useForm()
    const {enqueueSnackbar, closeSnackbar} = useSnackbar()

    const [modalVisible, setModalVisible] = useState(false)
    const [modalValue, setModalValue] = useState()

    const handelOpen = (e) => {
        xml_axios.post(`${SELL_TICKET}/${e["ticket_id"]}/${e["person_id"]}/${e["price"]}`)
            .then((response) => {
                const data = response.data
                setModalValue(data)
                setModalVisible(true)
            })
            .catch((err) => {
                let error = err.response.data
                let msg = error.message
                if(err.response.status == 404) {
                    msg = "Человек или билет с заданным id не найдены"
                }
                enqueueSnackbar(msg, {
                    autoHideDuration: 5000,
                    variant: "error"
                })
            })
    }

    const handleModalOk = () => {
        setModalVisible(false)
    }

    return (
        <>
            <Form form={form}
                  onFinish={handelOpen}
                  layout={"inline"}
                  labelCol={{span: 8}}
                  wrapperCol={{span: 8}}
            >
                <Form.Item label={"ID билета"}
                           name={"ticket_id"}
                           rules={[
                               {required: true, message: "Пожалуйста введите ID билета!"},
                               () => ({
                                   validator(_, value) {
                                       if (Number.isInteger(Number(value)) && value > 0) {
                                           return Promise.resolve();
                                       }
                                       return Promise.reject(new Error('ID должен быть целым большим 0!'));
                                   },
                               }),
                           ]}
                           style={{width: 200}}
                >
                    <InputNumber/>
                </Form.Item>
                <Form.Item label={"ID человека"}
                           name={"person_id"}
                           rules={[
                               {required: true, message: "Пожалуйста введите ID человека!"},
                               () => ({
                                   validator(_, value) {
                                       if (Number.isInteger(Number(value)) && value > 0) {
                                           return Promise.resolve();
                                       }
                                       return Promise.reject(new Error('ID должен быть целым большим 0!'));
                                   },
                               }),
                           ]}
                           style={{width: 200}}
                >
                    <InputNumber/>
                </Form.Item>
                <Form.Item label={"Price"}
                           name={"price"}
                           rules={[
                               {required: true, message: "Пожалуйста введите цену!"},
                               () => ({
                                   validator(_, value) {
                                       if (Number.isInteger(Number(value)) && value > 0) {
                                           return Promise.resolve();
                                       }
                                       return Promise.reject(new Error('Цена должна быть целой большей 0!'));
                                   },
                               }),
                           ]}
                           style={{width: 200}}
                >
                    <InputNumber/>
                </Form.Item>
                <Form.Item>
                    <Button type={"primary"} onClick={form.submit} style={{width: 200}}>
                        Продать билет
                    </Button>
                </Form.Item>
            </Form>
            <OperationResponseModal title={"Информация об операции"}
                                    visible={modalVisible}
                                    value={modalValue}
                                    handleOk={handleModalOk}
            />
        </>
    )
}