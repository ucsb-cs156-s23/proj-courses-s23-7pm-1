package edu.ucsb.cs156.courses.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ucsb.cs156.courses.collections.ConvertedSectionCollection;
import edu.ucsb.cs156.courses.collections.UpdateCollection;
import edu.ucsb.cs156.courses.services.UCSBCurriculumService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UpdateCourseDataJobFactory  {

    @Autowired
    private UCSBCurriculumService ucsbCurriculumService;

    @Autowired
    private ConvertedSectionCollection convertedSectionCollection;

    @Autowired
    private UpdateCollection updateCollection;

    public UpdateCourseDataJob create(String subjectArea, String quarterYYYYQ) {
        log.info("ucsbCurriculumService = " + ucsbCurriculumService);
        log.info("convertedSectionCollection = " + convertedSectionCollection);
        log.info("updateCollection = " + updateCollection);
        return new UpdateCourseDataJob(subjectArea, quarterYYYYQ, ucsbCurriculumService, convertedSectionCollection, updateCollection);
    }
}
