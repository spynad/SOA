import {useSnackbar} from "notistack";
import {useState} from "react";
import {PERSON_API, xml_axios} from "../../utils/api";
import {Button} from "antd";
import PersonForm from "./person-form";

export function AddPersonForm(){
    const {enqueueSnackbar, closeSnackbar} = useSnackbar()
    const [isAddTicketModalOpen, setIsAddTicketModalOpen] = useState(false)
    const showModifyTicketModal = () => {setIsAddTicketModalOpen(true)}
    const handleAddTicketOk = () => {setIsAddTicketModalOpen(false)}
    const handleAddTicketCancel = () => {setIsAddTicketModalOpen(false)}

    const onFormSubmit = (e) => {
        xml_axios.post(PERSON_API, {'person': e})
            .then((response) => {
                const newTicket = response.data.person
                enqueueSnackbar("Создан новый человек с id: " + newTicket.id, {
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
                Добавить человека
            </Button>
            <PersonForm formVisible={isAddTicketModalOpen}
                      onCancel={handleAddTicketCancel}
                      onFinish={onFormSubmit}
                      title={"Добавить человека"}
            />
        </>
    )

}