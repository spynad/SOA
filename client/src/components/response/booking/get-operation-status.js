import {useForm} from "antd/es/form/Form";
import {useSnackbar} from "notistack";
import {useState} from "react";
import {xml_axios, OPERATION_API} from "../../../utils/api";
import {Button, Form, InputNumber} from "antd";
import {OperationStatusResponseModal} from "../templates/operation-status-response-modal";

export function GetOperationStatus() {
    const [form] = useForm()
    const {enqueueSnackbar, closeSnackbar} = useSnackbar()

    const [modalVisible, setModalVisible] = useState(false)
    const [modalValue, setModalValue] = useState()

    const handelOpen = (e) => {
        xml_axios.get(`${OPERATION_API}/${e["id"]}`)
            .then((response) => {
                const data = response.data
                setModalValue(data)
                setModalVisible(true)
            })
            .catch((err) => {
                let error = err.response.data
                let msg = error.message
                if(err.response.status == 404) {
                    msg = "Операция с заданным id не найдена"
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
                <Form.Item label={"ID"}
                           name={"id"}
                           rules={[
                               {required: true, message: "Пожалуйста введите ID операции!"},
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
                        Статус операции
                    </Button>
                </Form.Item>
            </Form>
            <OperationStatusResponseModal title={"Информация о статусе операции"}
                                    visible={modalVisible}
                                    value={modalValue}
                                    handleOk={handleModalOk}
            />
        </>
    )
}