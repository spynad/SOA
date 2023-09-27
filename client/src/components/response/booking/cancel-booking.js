import {useForm} from "antd/es/form/Form";
import {useSnackbar} from "notistack";
import {useState} from "react";
import axios from "axios";
import {CANCEL_BOOKING} from "../../../utils/api";
import {Button, Form, InputNumber} from "antd";
import {OperationResponseModal} from "../templates/operation-response-modal";

export function CancelBooking() {
    const [form] = useForm()
    const {enqueueSnackbar, closeSnackbar} = useSnackbar()

    const [modalVisible, setModalVisible] = useState(false)
    const [modalValue, setModalValue] = useState()

    const handelOpen = (e) => {
        axios.post(`${CANCEL_BOOKING}/${e["id"]}/cancel`)
            .then((response) => {
                const data = response.data
                setModalValue(data)
                setModalVisible(true)
            })
            .catch((err) => {
                let error = err.response.data
                enqueueSnackbar(error.message, {
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
                <Form.Item label={"ID"}
                           name={"id"}
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
                <Form.Item>
                    <Button type={"primary"} onClick={form.submit} style={{width: 200}}>
                        Отменить бронирование билетов для человека
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