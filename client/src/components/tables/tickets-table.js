import {useSnackbar} from "notistack";
import {useEffect, useState} from "react";
import {TICKETS_API, xml_axios} from "../../utils/api"
    import {Button, Layout, Space, Table, Tag} from "antd";
import {ReloadOutlined} from "@ant-design/icons";
import {filtersMapToLHSBrackets, filtersToLHSBrackets, parseSorterToQueryParam} from "../../utils/tables/sort-and-filter";
import {getColumnSearchProps} from "./column-search";

const {Sider} = Layout;

export default function TicketsTable(){
    const {enqueueSnackbar, closeSnackbar} = useSnackbar();

    const [sortQueryParams, setSortQueryParams] = useState([]);
    const [filterModel, setFilterModel] = useState(new Map());
    const [currentPage, setCurrentPage] = useState(1);
    const [pageSize, setPageSize] = useState(5);
    const [data, setData] = useState([]);
    const [totalCount, setTotalCount] = useState(0);
    const [loading, setLoading] = useState(true);
    const [collapsed, setCollapsed] = useState(false)

    useEffect(() => {
        getData(currentPage, pageSize, sortQueryParams, filtersMapToLHSBrackets(filterModel));
    }, [pageSize, currentPage, filterModel, sortQueryParams])

    const getData = (page, pageSizeT, sort, filter) => {
        const queryParams = new URLSearchParams();

        queryParams.append("page", page);
        queryParams.append("pageSize", pageSizeT);

        if (sort?.length){
            sort.forEach((elem) => {
                queryParams.append("sort", elem)
            })
        }

        if (filter) {
            filter.forEach((elem) => {
                queryParams.append("filter", elem)
            })
        }

        setLoading(true)

        xml_axios.get(TICKETS_API, {params: queryParams})
            .then((response) => {
                let res = response.data
                if (response.data.getAllTicketsResponse.return[0]["status"][0] === '400') {
                    enqueueSnackbar("Некорректные параметры фильтров", {
                        autoHideDuration: 5000,
                        variant: "error"
                    })
                    setLoading(false)
                    return;
                }
                let tickets = response.data.getAllTicketsResponse.return[0].result[0].tickets
                if (tickets == null) {
                    setPageSize(5);
                    setCurrentPage(1);
                    setSortQueryParams([]);
                    setFilterModel(new Map());
                    enqueueSnackbar("Билеты не найдены", {
                                        autoHideDuration: 5000,
                                        variant: "error"
                                    })
                    return;
                }
                // if (Object.prototype.toString.call(response.data["return"]["result"]["tickets"]) !== '[object Array]') {
                //     response.data["return"]["result"]["tickets"] = [response.data["return"]["result"]["tickets"],]
                // }
                setData(tickets.map((elem) =>{
                    if(elem.person)
                        elem.person = elem.person[0]
                    if(elem.coordinates)
                        elem.coordinates = elem.coordinates[0]
                    return {
                        ...elem,
                        key: elem.id
                    }
                }))
                setTotalCount(response.data.getAllTicketsResponse.return[0].result[0]["pagesTotal"])
                setLoading(false)
            })
    }

    const handleTableChange = (pagination, filters, sorter) => {
        let sortQueryParams = []
        let filterParams = []

        const sortersArray = Array.from(sorter)

        if (sorter.hasOwnProperty("column")) {
            sortQueryParams[0] = parseSorterToQueryParam(sorter)
        } else if (sortersArray.length > 0){
            sortQueryParams = sortersArray.map((elem) => {
                return parseSorterToQueryParam(elem)
            })
        }

        if (filters) {
            filterParams = filtersToLHSBrackets(filters)
        }

        let newFilterModel = new Map()

        Object.keys(filters).forEach((key) => {
            if (key && filters[key]) {
                newFilterModel.set(key, [String([filters[key][0]]), filters[key][1]])
            }
        })

        setSortQueryParams(sortQueryParams)
        setFilterModel(newFilterModel)

        //getData(currentPage, pageSize, sortQueryParams, filterParams)
    }

    const handleFilterChange = (dataIndex, filterType, filterValue) => {
        if (filterType && filterValue) {
            const filtrationChanged = filterModel?.get(dataIndex) &&
                (
                    filterModel?.get(dataIndex)[0] !== filterType ||
                    filterModel?.get(dataIndex)[1] !== filterValue
                )

            if (filtrationChanged) {
                filterModel.set(dataIndex, [filterType, filterValue])
                getData(currentPage, pageSize, sortQueryParams, filtersMapToLHSBrackets(filterModel))
                setFilterModel(filterModel)
            }
        }
    }

    const handleRefresh = () => {
        getData(currentPage, pageSize, sortQueryParams, filtersMapToLHSBrackets(filterModel))
    }

    return (
        <>
            <Table columns={[
                {
                    title: "ID",
                    dataIndex: "id",
                    key: "id",
                    sorter: {multiple: 3},
                    sortDirections: ["ascend", "descend"],
                    fixed: "left",
                    ...getColumnSearchProps("id", handleFilterChange)
                },
                {
                    title: "Название",
                    dataIndex: "name",
                    key: "name",
                    sorter: {multiple: 4},
                    sortDirections: ["ascend", "descend"],
                    // fixed: "left",
                    ...getColumnSearchProps("name", handleFilterChange)
                },
                {
                    title: "Дата создания",
                    dataIndex: "creationDate",
                    key: "creationDate",
                    sorter: {multiple: 3},
                    sortDirections: ["ascend", "descend"],
                    ...getColumnSearchProps("creationDate", handleFilterChange),
                    children: [
                        {
                            title: "Время",
                            dataIndex: ["creationDate"],
                            key: "creationDate.time",
                            // render: (text, row) => {
                            //     let date = text.split("T")[1].split(".")
                            //     return `${date[0]}`
                            // }
                        },
                        {
                            title: "Дата",
                            dataIndex: ["creationDate"],
                            key: "creationDate.date",
                            // render: (text, row) => {
                            //     let date = text.split("T")
                            //     return `${date[0]}`
                            // }
                        },
                    ]
                },
                {
                    title: "Скидка",
                    dataIndex: "discount",
                    key: "discount",
                    sorter: {multiple: 3},
                    sortDirections: ["ascend", "descend"],
                    ...getColumnSearchProps("discount", handleFilterChange)
                },
                {
                    title: "Тип",
                    dataIndex: "typeStr",
                    key: "typeStr",
                    sorter: {multiple: 3},
                    sortDirections: ["ascend", "descend"],
                    ...getColumnSearchProps("type", handleFilterChange)
                },
                {
                    title: "Возвратный",
                    dataIndex: "refundable",
                    key: "refundable",
                    sorter: {multiple: 3},
                    sortDirections: ["ascend", "descend"],
                    // render: (text, record) => (
                    //     <Tag color={text ? 'green' : 'volcano'}>
                    //         {text[0].toString()}
                    //     </Tag>
                    // ),
                    ...getColumnSearchProps("refundable", handleFilterChange)
                },
                {
                    title: "Цена",
                    dataIndex: "price",
                    key: "price",
                    sorter: {multiple: 3},
                    sortDirections: ["ascend", "descend"],
                    ...getColumnSearchProps("price", handleFilterChange)
                },
                {
                    title: "Координаты",
                    children: [
                        {
                            title: "X",
                            dataIndex: ["coordinates", "x"],
                            key: "coordinates.x",
                            sorter: {multiple: 3},
                            sortDirections: ["ascend", "descend"],
                            ...getColumnSearchProps("coordinates.x", handleFilterChange)
                        },
                        {
                            title: "Y",
                            dataIndex: ["coordinates", "y"],
                            key: "coordinates.y",
                            sorter: {multiple: 3},
                            sortDirections: ["ascend", "descend"],
                            ...getColumnSearchProps("coordinates.y", handleFilterChange)
                        },
                    ]
                },
                {
                    title: "Человек",
                    children: [
                        {
                            title: "ID",
                            dataIndex: ["person", "id"],
                            key: "person.id",
                            sorter: {multiple: 3},
                            sortDirections: ["ascend", "descend"],
                            ...getColumnSearchProps("person.id", handleFilterChange)
                        },
                        {
                            title: "Вес",
                            dataIndex: ["person", "weight"],
                            key: "person.weight",
                            sorter: {multiple: 3},
                            sortDirections: ["ascend", "descend"],
                            ...getColumnSearchProps("person.weight", handleFilterChange)
                        },
                        {
                            title: "Цвет глаз",
                            dataIndex: ["person", "eyeColor"],
                            key: "person.eyeColor",
                            sorter: {multiple: 3},
                            sortDirections: ["ascend", "descend"],
                            ...getColumnSearchProps("person.eyeColor", handleFilterChange)
                        },
                        {
                            title: "Цвет волос",
                            dataIndex: ["person", "hairColor"],
                            key: "person.hairColor",
                            sorter: {multiple: 3},
                            sortDirections: ["ascend", "descend"],
                            ...getColumnSearchProps("person.hairColor", handleFilterChange)
                        },
                        {
                            title: "Национальность",
                            dataIndex: ["person", "country"],
                            key: "person.country",
                            sorter: {multiple: 3},
                            sortDirections: ["ascend", "descend"],
                            ...getColumnSearchProps("person.country", handleFilterChange)
                        },
                    ]
                }
            ]}
                   rowKey={"key"}
                   dataSource={data}
                   onChange={handleTableChange}
                   loading={loading}
                   bordered={true}
                   scroll={{x: 'max-content'}}
                   pagination={{
                       pageSizeOptions: [5, 10, 20, 50, 100],
                       showSizeChanger: true,
                       onChange: (newPage, newPageSize) =>  {
                        setCurrentPage(newPage);
                        setPageSize(newPageSize);
                       },
                       total: totalCount * pageSize,
                       pageSize: pageSize,
                       showTotal: (total, range) => `${range[0]}-${range[1]} of ${total} items`
                   }}
            />
            <Space style={{
                display: "flex",
                alignItems: "center",
                justifyContent: "center"
            }}>
                <Button icon={<ReloadOutlined/>} onClick={handleRefresh} style={{}}>
                    Обновить
                </Button>
            </Space>
        </>
    )
}