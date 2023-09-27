import {Button, Descriptions, Modal, Typography} from "antd";


export function SimpleTicketResponseModal({title, value, visible, handleOk}){
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
                        {getDescription("Name", value?.name)}
                        {getDescription("Coordinates X", value?.coordinates?.x)}
                        {getDescription("Coordinates Y", value?.coordinates?.y)}
                        {getDescription("Creation date", value?.creationDate)}
                        {getDescription("Number of rooms", value?.numberOfRooms)}
                        {getDescription("Price", value?.price)}
                        {getDescription("Discount", value?.discount)}
                        {getDescription("Refundable", value?.refundable.toString())}
                        {getDescription("Type", value?.type)}
                        {getDescription("Person weight", value?.person?.weight)}
                        {getDescription("Person eye color", value?.person?.eyeColor)}
                        {getDescription("Person hair color", value?.person?.hairColor)}
                        {getDescription("Person nationality", value?.person?.nationality)}
                    </Descriptions> : <Typography>Нет ответа</Typography>
                }
            </Modal>
        </>
    )
}