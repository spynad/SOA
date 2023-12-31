import {useSnackbar} from "notistack";
import {useState} from "react";
import {TICKETS_API, xml_axios} from "../../utils/api";
import {Button} from "antd";
import TicketForm from "./ticket-form";

export function AddTicketForm(){
    const {enqueueSnackbar, closeSnackbar} = useSnackbar()
    const [isAddTicketModalOpen, setIsAddTicketModalOpen] = useState(false)
    const showModifyTicketModal = () => {setIsAddTicketModalOpen(true)}
    const handleAddTicketOk = () => {setIsAddTicketModalOpen(false)}
    const handleAddTicketCancel = () => {setIsAddTicketModalOpen(false)}

    const onFormSubmit = (e) => {
        xml_axios.post(TICKETS_API, {'ticket': e})
            .then((response) => {
                if(response.data.deletePersonResponse.return[0]["status"][0] === '404') {
                    let msg = "Человек с заданным id не найден"
                    enqueueSnackbar(msg, {
                        autoHideDuration: 5000,
                        variant: "error"
                    })
                    return
                }
                const newTicket = response.data.addTicketResponse.return[0].result[0]
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