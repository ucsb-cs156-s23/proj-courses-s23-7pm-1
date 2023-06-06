import UpdatesTable from "main/components/Updates/UpdatesTable";

export default function CoursesUpdatesPage({}) {
    const updates = [
        {
            "subject_area" : "CMPSC",
            "quarter_yyyyq": "20234",
            "last_update": "2023-05-13T10:22:44"
          },
          {
            "subject_area" : "ECE",
            "quarter_yyyyq": "20232",
            "last_update": "2023-04-22T12:22:35"
          },
          {
            "subject_area" : "ANTH",
            "quarter_yyyyq": "20221",
            "last_update": "2022-01-10T10:30:35"
          }
    ]
  return (
    <UpdatesTable updates={updates} />
  )
}