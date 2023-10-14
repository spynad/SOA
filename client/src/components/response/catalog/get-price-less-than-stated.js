import {useSnackbar} from "notistack";
import {useState} from "react";
import {xml_axios, CHEAPER_TICKETS} from "../../../utils/api";
import {Button, Form} from "antd";
import {SimpleResponseModal} from "../templates/simple-response-modal";
import {InputNumber} from "antd/es";
import {useForm} from "antd/es/form/Form";

export function GetPriceLessThanStatedCount(){
    const [form] = useForm()
    const {enqueueSnackbar, closeSnackbar} = useSnackbar()

    const [modalVisible, setModalVisible] = useState(false)
    const [modalValue, setModalValue] = useState()

    const handelOpen = (e) => {
        xml_axios.post(`${CHEAPER_TICKETS}/${e["price"]}`)
            .then((response) => {
                const data = response.data.result.result
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
                <Form.Item label={"Цена"}
                           name={"price"}
                           rules={[
                               {required: true, message: "Пожалуйста введите цену!"},
                               () => ({
                                   validator(_, value) {
                                       if (Number.isInteger(Number(value)) && value > 0) {
                                           return Promise.resolve();
                                       }
                                       return Promise.reject(new Error('Цена должна быть больше 0!'));
                                   },
                               }),
                           ]}
                           style={{width: 200}}
                >
                    <InputNumber/>
                </Form.Item>
                <Form.Item>
                    <Button type={"primary"} onClick={form.submit} style={{width: 200}}>
                        Билеты дешевле
                    </Button>
                </Form.Item>
            </Form>
            <SimpleResponseModal title={"Количество билетов дешевле заданной цены"}
                                 visible={modalVisible}
                                 value={modalValue}
                                 handleOk={handleModalOk}
            />
        </>
    )
}