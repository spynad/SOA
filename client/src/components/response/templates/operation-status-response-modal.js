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
                    value ? <Descriptions title={"Операция"} layout={"inline"}>
                        {getDescription("ID", value.operation?.id)}
                        {getDescription("Время начала исполнения", value.operation?.time)}
                        {getDescription("Время завершения исполнения", value.operation?.finishedAt)}
                        {getDescription("Статус", value.operation?.status)}
                    </Descriptions> : <Typography>Нет ответа</Typography>
                }
            </Modal>
        </>
    )
}