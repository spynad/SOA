import {Button, Descriptions, Modal, Typography} from "antd";

export function OperationStatusResponseModal({title, value, visible, handleOk}){

    const getDescription = (label, value) => {
        return (
            value ? <Descriptions.Item label={label}>{value}</Descriptions.Item> : <></>
        )
    }

    return (
        <>
            <Modal open={visible}
                   title={title}
                   onCancel={handleOk}
                   footer={[
                       <Button type={"primary"} key={"submit"} onClick={handleOk}>
                           Ок
                       </Button>
                   ]}
                   width={1000}
                   bordered
            >
                {
                    value ? <Descriptions title={"Информация о билете"} layout={"inline"}>
                        {getDescription("ID", value?.id)}
                        {getDescription("Status update time", value?.time)}
                        {getDescription("Status", value?.status)}
                    </Descriptions> : <Typography>Нет ответа</Typography>
                }
            </Modal>
        </>
    )
}