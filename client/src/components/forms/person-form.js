import {useForm} from "antd/es/form/Form";
import {useEffect} from "react";
import {Form, Input, InputNumber, Modal, Radio, Select} from "antd";

export default function PersonForm({formVisible, onCancel, onFinish, title, initialValues}){
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
                    <Form.Item label={"Цвет глаз"}
                               name={"eyeColor"}
                    >
                        <Select>
                            <Select.Option value={null}>-</Select.Option>
                            <Select.Option value={"GREEN"}>Зеленый</Select.Option>
                            <Select.Option value={"RED"}>Красный</Select.Option>
                            <Select.Option value={"ORANGE"}>Оранжевый</Select.Option>
                        </Select>
                    </Form.Item>
                    <Form.Item label={"Цвет волос"}
                               name={"hairColor"}
                    >
                        <Select>
                            <Select.Option value={"YELLOW"}>Желтый</Select.Option>
                            <Select.Option value={"RED"}>Красный</Select.Option>
                            <Select.Option value={"ORANGE"}>Оранжевый</Select.Option>
                            <Select.Option value={"BROWN"}>Коричневый</Select.Option>
                        </Select>
                    </Form.Item>
                    <Form.Item label={"Национальность"}
                               name={"country"}
                    >
                        <Select>
                            <Select.Option value={null}>-</Select.Option>
                            <Select.Option value={"RUSSIA"}>Россия</Select.Option>
                            <Select.Option value={"USA"}>США</Select.Option>
                            <Select.Option value={"FRANCE"}>Франция</Select.Option>
                            <Select.Option value={"SPAIN"}>Испания</Select.Option>
                            <Select.Option value={"JAPAN"}>Япония</Select.Option>
                        </Select>
                    </Form.Item>
                    <Form.Item label={"Вес"}
                               name={"weight"}
                               rules={[
                                   {required: true, message: "Пожалуйста введите вес!"},
                                   () => ({
                                       validator(_, value) {
                                           if (value >= 0) {
                                               return Promise.resolve();
                                           }
                                           return Promise.reject(new Error('Вес должен быть больше 0!'));
                                       },
                                   })
                               ]}
                    >
                        <InputNumber/>
                    </Form.Item>
                </Form>
            </Modal>
        </>
    )
}