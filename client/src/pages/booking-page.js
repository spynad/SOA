import {Divider, Layout, Space, Typography} from "antd";
import {Content} from "antd/es/layout/layout";
import MyFooter from "../components/main/footer";
import MyHeader from "../components/main/header";
import {CancelBooking} from "../components/response/booking/cancel-booking";
import {SellTicket} from "../components/response/booking/sell-ticket";
import {GetOperationStatus} from "../components/response/booking/get-operation-status";
import {CancelOperation} from "../components/response/booking/cancel-operation";

export function BookingPage(){
    return (
        <>
            <Layout style={{minHeight: "100vh"}}>
                <MyHeader selectedMenuItem={'booking'}/>
                <Content>
                    <Space style={{
                        marginTop: 50,
                        display: "flex",
                        alignItems: "center",
                        justifyContent: "center",
                        flexDirection: "column"
                    }}
                           size={0}
                    >
                        <Typography style={{marginBottom: 50, fontSize: "2vh", fontWeight: "bold"}}>Меню Бронирования</Typography>
                        <CancelBooking/>
                        <Divider style={{width: 800}}/>
                        <SellTicket/>
                        <Divider style={{width: 800}}/>
                        <GetOperationStatus/>
                        <Divider style={{width: 800}}/>
                        <CancelOperation/>
                    </Space>
                </Content>
                <MyFooter/>
            </Layout>
        </>
    )
}