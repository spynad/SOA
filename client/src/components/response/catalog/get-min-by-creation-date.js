import {useSnackbar} from "notistack";
import {useState} from "react";
import axios from "axios";
import {Button} from "antd";
import {SimpleTicketResponseModal} from "../templates/simple-ticket-response-modal";
import {MINIMAL_BY_CREATION_DATE} from "../../../utils/api";

export function GetMinByCreationDate(){

    const {enqueueSnackbar, closeSnackbar} = useSnackbar()

    const [modalVisible, setModalVisible] = useState(false)
    const [modalValue, setModalValue] = useState()

    const handelOpen = (e) => {
        axios.get(`${MINIMAL_BY_CREATION_DATE}`)
            .then((response) => {
                const data = response.data
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
            <Button type={"primary"} onClick={handelOpen}>
                Получить самый старый билет
            </Button>
            <SimpleTicketResponseModal title={"Полученный результат"}
                                       visible={modalVisible}
                                       value={modalValue}
                                       handleOk={handleModalOk}
            />
        </>
    )
}