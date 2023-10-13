import {useForm} from "antd/es/form/Form";
import {useEffect} from "react";
import {Form, Input, InputNumber, Modal, Radio, Select} from "antd";

export default function TicketForm({formVisible, onCancel, onFinish, title, initialValues}){
    const [form] = useForm()

    useEffect(() => {
        if (initialValues) {
            form.setFieldsValue(initialValues)
        }
    }, [initialValues])

    return (
        <>
            <Modal title={title}
                   open={formVisible}
                   onOk={form.submit}
                   onCancel={onCancel}
                   width={1000}
            >
                <Form form={form}
                      onFinish={onFinish}
                      labelCol={{span: 4}}
                      wrapperCol={{span: 8}}
                      layout={"horizontal"}
                >
                    <Form.Item label={"Название"}
                               name={"name"}
                               rules={[
                                   {required: true, message: "Пожалуйста введите название!"},
                                   {min: 1, message: "Строка названия не может быть пустой!"},
                               ]}
                    >
                        <Input/>
                    </Form.Item>
                    <Form.Item label={"Возвратный"}
                               name={"refundable"}
                               rules={[
                                   {required: true, message: "Пожалуйста выберите, возвратный ли билет!"},
                               ]}
                    >
                        <Radio.Group>
                            <Radio value={true}>Да</Radio>
                            <Radio value={false}>Нет</Radio>
                        </Radio.Group>
                    </Form.Item>
                    <Form.Item label={"Тип"}
                               name={"type"}
                    >
                        <Select>
                            <Select.Option value={null}>-</Select.Option>
                            <Select.Option value={"VIP"}>VIP</Select.Option>
                            <Select.Option value={"USUAL"}>Обычный</Select.Option>
                            <Select.Option value={"BUDGETARY"}>Бюджетный</Select.Option>
                            <Select.Option value={"CHEAP"}>Дешевый</Select.Option>
                        </Select>
                    </Form.Item>
                    <Form.Item label={"Цена"}
                               name={"price"}
                               rules={[
                                   {required: true, message: "Пожалуйста введите цену!"},
                                   () => ({
                                       validator(_, value) {
                                           if (value >= 0) {
                                               return Promise.resolve();
                                           }
                                           return Promise.reject(new Error('Цена должна быть больше 0!'));
                                       },
                                   })
                               ]}
                    >
                        <InputNumber/>
                    </Form.Item>
                    <Form.Item label={"Скидка"}
                               name={"discount"}
                               rules={[
                                   {required: true, message: "Пожалуйста введите скидку!"},
                                   () => ({
                                       validator(_, value) {
                                           if (Number.isInteger(Number(value)) && value > 0 && value <= 100) {
                                               return Promise.resolve();
                                           }
                                           return Promise.reject(new Error('Скидка должна быть целым числом, большей 0 и не большей 100!'));
                                       },
                                   })
                               ]}
                    >
                        <InputNumber/>
                    </Form.Item>
                    <Form.Item label="Координаты">
                        <Input.Group>
                            <Form.Item
                                label="X"
                                name={["coordinates", "x"]}
                                rules={[
                                    {required: true, message: 'Пожалуйста введите X!'},
                                ]}
                            >
                                <InputNumber/>
                            </Form.Item>
                            <Form.Item
                                label="Y"
                                name={["coordinates", "y"]}
                                rules={[
                                    {required: true, message: 'Пожалуйста введите Y!'},
                                    () => ({
                                        validator(_, value) {
                                            if (Number.isInteger(Number(value)) && Number(value) > -236) {
                                                return Promise.resolve();
                                            }
                                            return Promise.reject(new Error("X должен быть целым числом и больше -236!"));
                                        },
                                    })
                                ]}
                            >
                                <InputNumber/>
                            </Form.Item>
                        </Input.Group>
                    </Form.Item>
                    <Form.Item label="Человек">
                        <Input.Group>
                            <Form.Item
                                label="ID"
                                name={["person", "id"]}
                                rules={[
                                    {required: true, message: 'Пожалуйста id владельца!'},
                                ]}
                            >
                                <Input/>
                            </Form.Item>
                        </Input.Group>
                    </Form.Item>
                </Form>
            </Modal>
        </>
    )
}