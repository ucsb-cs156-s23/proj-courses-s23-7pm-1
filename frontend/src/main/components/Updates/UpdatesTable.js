import React from "react";
import OurTable, {PlaintextColumn, DateColumn} from "main/components/OurTable";

export default function UpdatesTable({ updates }) {

    const testid = "UpdatesTable";

    const columns = [
        {
            Header: 'Subject Area',
            accessor: 'subject_area', // accessor is the "key" in the data
        },
        {
            Header:'Quarter',
            accessor: 'quarter_yyyyq'
        },
        {
          Header:'Last Update',
          accessor: 'last_update'
        },
        
    ];
    
    const sortees = React.useMemo(
        () => [
          {
            id: "subject_area",
            desc: true
          }
        ],
       // Stryker disable next-line all
        []
      );

    return <OurTable
        data={updates}
        columns={columns}
        testid={testid}
        initialState={{ sortBy: sortees }}
    />;
}; 
