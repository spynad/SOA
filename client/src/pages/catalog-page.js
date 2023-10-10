import {Divider, Layout, Space} from "antd";
import MyHeader from "../components/main/header";
import MyFooter from "../components/main/footer";
import TicketsTable from "../components/tables/tickets-table";
import {AddTicketForm} from "../components/forms/add-ticket-form";
import {AddPersonForm} from "../components/forms/add-person-form";
import {DeleteTicketForm} from "../components/forms/delete-ticket-form";
import {DeletePersonForm} from "../components/forms/delete-person-form";
import {UpdateTicketForm} from "../components/forms/update-ticket-form";
import {UpdatePersonForm} from "../components/forms/update-person-form";
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
                                <AddPersonForm/>
                                <Divider/>
                                <UpdateTicketForm/>
                                <Divider/>
                                <UpdatePersonForm/>
                                <Divider/>
                                <DeleteTicketForm/>
                                <Divider/>
                                <DeletePersonForm/>
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