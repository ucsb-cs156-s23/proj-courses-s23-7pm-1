package edu.ucsb.cs156.courses.collections;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import edu.ucsb.cs156.courses.documents.Update;

@Repository
public interface UpdateCollection extends MongoRepository<Update, ObjectId> {
    @Query("{'subjectArea': ?0, 'quarterYYYYQ': ?1}")
    Optional<Update> findOneBySubjectAreaAndQuarterYYYYQ(String subjectArea, String quarterYYYYQ);
}
