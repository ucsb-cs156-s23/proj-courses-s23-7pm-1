package edu.ucsb.cs156.courses.documents;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "updates")
public class Update {
    private ObjectId _id;
    private String subjectArea;
    private String quarterYYYYQ;
    private ZonedDateTime lastUpdate;
}
