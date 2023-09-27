import {useSnackbar} from "notistack";
import {useState} from "react";
import axios from "axios";
import {AVERAGE_DISCOUNT} from "../../../utils/api";
import {Button} from "antd";
import {SimpleResponseModal} from "../templates/simple-response-modal";

export function GetAverageDiscount(){
    const {enqueueSnackbar, closeSnackbar} = useSnackbar()

    const [modalVisible, setModalVisible] = useState(false)
    const [modalValue, setModalValue] = useState()

    const handelOpen = () => {
        axios.post(AVERAGE_DISCOUNT)
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
                Вычислить среднюю скидку
            </Button>
            <SimpleResponseModal title={"Средняя скидка"}
                                 visible={modalVisible}
                                 value={modalValue}
                                 handleOk={handleModalOk}
            />
        </>
    )
}