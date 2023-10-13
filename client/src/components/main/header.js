import {Layout, Menu, Switch} from "antd";
import {CalculatorOutlined, TableOutlined} from "@ant-design/icons";
import {Link} from "react-router-dom";
import {useThemeSwitcher} from "react-css-theme-switcher";
import {useState} from "react";

const {Header} = Layout

export default function MyHeader({selectedMenuItem}){

    const rightStyle = {position: 'absolute', top: 0, right: 0}

    const items = [
        {
            icon: <TableOutlined/>,
            label: (
                <Link to={"/catalog"}>
                    Каталог
                </Link>
            ),
            key: "catalog"
        },
        {
            icon: <CalculatorOutlined/>,
            label: (
                <Link to={"/booking"}>
                    Бронирование
                </Link>
            ),
            key: "booking"
        },
    ];

    return (
        <>
            <Header>
                <Menu
                    mode={"horizontal"}
                    items={items}
                    defaultSelectedKeys={[selectedMenuItem]}
                />
                <Menu selectable={false} theme={"dark"} mode='horizontal' style={rightStyle}>
                </Menu>
            </Header>
        </>
    )
}