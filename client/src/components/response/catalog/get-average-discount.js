import {useSnackbar} from "notistack";
import {useState} from "react";
import {xml_axios, AVERAGE_DISCOUNT} from "../../../utils/api";
import {Button} from "antd";
import {SimpleResponseModal} from "../templates/simple-response-modal";

export function GetAverageDiscount(){
    const {enqueueSnackbar, closeSnackbar} = useSnackbar()

    const [modalVisible, setModalVisible] = useState(false)
    const [modalValue, setModalValue] = useState()

    const handelOpen = () => {
        xml_axios.post(AVERAGE_DISCOUNT)
            .then((response) => {
                const data = response.data.result.result
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