import {useSnackbar} from "notistack";
import {useState} from "react";
import {TICKETS_API} from "../../utils/api";
import axios from "axios";
import {Button} from "antd";
import TicketForm from "./ticket-form";

export function AddTicketForm(){
    const {enqueueSnackbar, closeSnackbar} = useSnackbar()
    const [isAddTicketModalOpen, setIsAddTicketModalOpen] = useState(false)
    const showModifyTicketModal = () => {setIsAddTicketModalOpen(true)}
    const handleAddTicketOk = () => {setIsAddTicketModalOpen(false)}
    const handleAddTicketCancel = () => {setIsAddTicketModalOpen(false)}

    const onFormSubmit = (e) => {
        axios.post(TICKETS_API, e)
            .then((response) => {
                const newTicket = response.data
                enqueueSnackbar("Создан новый билет с id: " + newTicket.id, {
                    autoHideDuration: 5000,
                    variant: "success"
                })
                handleAddTicketOk()
            })
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
            <Button type={"primary"} onClick={showModifyTicketModal} style={{width: 150, flex: 1}}>
                Добавить билет
            </Button>
            <TicketForm formVisible={isAddTicketModalOpen}
                      onCancel={handleAddTicketCancel}
                      onFinish={onFormSubmit}
                      title={"Добавить билет"}
            />
        </>
    )

}