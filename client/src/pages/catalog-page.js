import {Divider, Layout, Space} from "antd";
import MyHeader from "../components/main/header";
import MyFooter from "../components/main/footer";
import TicketsTable from "../components/tables/tickets-table";
import {AddTicketForm} from "../components/forms/add-ticket-form";
import {DeleteTicketForm} from "../components/forms/delete-ticket-form";
import {UpdateTicketForm} from "../components/forms/update-ticket-form";
import {GetMinByCreationDate} from "../components/response/catalog/get-min-by-creation-date";
import {GetAverageDiscount} from "../components/response/catalog/get-average-discount";
import {GetPriceLessThanStatedCount} from "../components/response/catalog/get-price-less-than-stated";

const {Content} = Layout;

export default function TicketsCatalogPage(){
    return (
        <>
            <Layout style={{minHeight: "100vh"}}>
                <MyHeader selectedMenuItem={'catalog'}/>
                <Layout>
                    <Layout
                        style={{
                            padding: '24px 24px 24px',
                        }}
                    >
                        <Content>
                            <TicketsTable pageSize={5}/>
                            <Divider/>
                            <Space style={{
                                display: "flex",
                                alignItems: "center",
                                justifyContent: "center",
                                flexDirection: "column",
                            }}
                                   size={0}
                            >
                                <AddTicketForm/>
                                <Divider/>
                                <UpdateTicketForm/>
                                <Divider/>
                                <DeleteTicketForm/>
                                <Divider/>
                                <GetMinByCreationDate/>
                                <Divider/>
                                <GetAverageDiscount/>
                                <Divider/>
                                <GetPriceLessThanStatedCount/>
                            </Space>
                        </Content>
                        <MyFooter/>
                    </Layout>
                </Layout>
            </Layout>
        </>
    )
}