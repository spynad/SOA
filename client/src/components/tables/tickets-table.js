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
                if (Object.prototype.toString.call(response.data["ticketsArray"]["tickets"]) !== '[object Array]') {
                    response.data["ticketsArray"]["tickets"] = [response.data["ticketsArray"]["tickets"],]
                }
                setData(response.data["ticketsArray"]["tickets"].map((elem) =>{
                    return {
                        ...elem,
                        key: elem.id
                    }
                }))
                setTotalCount(response.data["ticketsArray"]["pagesTotal"])
                setLoading(false)
            })
            .catch((err) => {
                let error = err.response.data
                enqueueSnackbar(error.message, {
                    autoHideDuration: 5000,
                    variant: "error"
                })
                if (error){
                    setLoading(false)
                }
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
                    title: "Name",
                    dataIndex: "name",
                    key: "name",
                    sorter: {multiple: 4},
                    sortDirections: ["ascend", "descend"],
                    // fixed: "left",
                    ...getColumnSearchProps("name", handleFilterChange)
                },
                {
                    title: "Creation date",
                    dataIndex: "creationDate",
                    key: "creationDate",
                    sorter: {multiple: 3},
                    sortDirections: ["ascend", "descend"],
                    ...getColumnSearchProps("creationDate", handleFilterChange),
                    children: [
                        {
                            title: "Time",
                            dataIndex: ["creationDate"],
                            key: "creationDate.time",
                            render: (text, row) => {
                                let date = text.split("T")[1].split(".")
                                return `${date[0]}`
                            }
                        },
                        {
                            title: "Date",
                            dataIndex: ["creationDate"],
                            key: "creationDate.date",
                            render: (text, row) => {
                                let date = text.split("T")
                                return `${date[0]}`
                            }
                        },
                    ]
                },
                {
                    title: "Discount",
                    dataIndex: "discount",
                    key: "discount",
                    sorter: {multiple: 3},
                    sortDirections: ["ascend", "descend"],
                    ...getColumnSearchProps("discount", handleFilterChange)
                },
                {
                    title: "Type",
                    dataIndex: "type",
                    key: "type",
                    sorter: {multiple: 3},
                    sortDirections: ["ascend", "descend"],
                    ...getColumnSearchProps("type", handleFilterChange)
                },
                {
                    title: "Refundable",
                    dataIndex: "refundable",
                    key: "refundable",
                    sorter: {multiple: 3},
                    sortDirections: ["ascend", "descend"],
                    render: (text, record) => (
                        <Tag color={text ? 'green' : 'volcano'}>
                            {text.toString()}
                        </Tag>
                    ),
                    ...getColumnSearchProps("refundable", handleFilterChange)
                },
                {
                    title: "Price",
                    dataIndex: "price",
                    key: "price",
                    sorter: {multiple: 3},
                    sortDirections: ["ascend", "descend"],
                    ...getColumnSearchProps("price", handleFilterChange)
                },
                {
                    title: "Coordinates",
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
                    title: "Person",
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
                            title: "Weight",
                            dataIndex: ["person", "weight"],
                            key: "person.weight",
                            sorter: {multiple: 3},
                            sortDirections: ["ascend", "descend"],
                            ...getColumnSearchProps("person.weight", handleFilterChange)
                        },
                        {
                            title: "Eye color",
                            dataIndex: ["person", "eyeColor"],
                            key: "person.eyeColor",
                            sorter: {multiple: 3},
                            sortDirections: ["ascend", "descend"],
                            ...getColumnSearchProps("person.eyeColor", handleFilterChange)
                        },
                        {
                            title: "Hair color",
                            dataIndex: ["person", "hairColor"],
                            key: "person.hairColor",
                            sorter: {multiple: 3},
                            sortDirections: ["ascend", "descend"],
                            ...getColumnSearchProps("person.hairColor", handleFilterChange)
                        },
                        {
                            title: "Country",
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
                    Refresh
                </Button>
            </Space>
        </>
    )
}