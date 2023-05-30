package edu.ucsb.cs156.courses.jobs;

import java.util.List;
import java.util.Optional;


import edu.ucsb.cs156.courses.entities.UCSBSubject;
import edu.ucsb.cs156.courses.collections.ConvertedSectionCollection;
import edu.ucsb.cs156.courses.documents.ConvertedSection;
import edu.ucsb.cs156.courses.services.UCSBSubjectsService;
import edu.ucsb.cs156.courses.services.UCSBCurriculumService;
import edu.ucsb.cs156.courses.services.jobs.JobContext;
import edu.ucsb.cs156.courses.services.jobs.JobContextConsumer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


@AllArgsConstructor
@Slf4j
public class UpdateCourseDataWithQuarterJob implements JobContextConsumer {

    @Getter private String quarterYYYYQ;
    @Getter private UCSBSubjectsService ucsbSubjectService;
    @Getter private UCSBCurriculumService ucsbCurriculumService;
    @Getter private ConvertedSectionCollection convertedSectionCollection;

    @Override
    public void accept(JobContext ctx) throws Exception {
        ctx.log("Updating quarter courses for [" + quarterYYYYQ + "]");

        List<UCSBSubject> ucsbsubjects = ucsbSubjectService.get();
        for (UCSBSubject subject : ucsbsubjects){
            String subjectArea = subject.getSubjectCode();

            ctx.log("Updating courses for [" + subjectArea + " " + quarterYYYYQ + "]");

            List<ConvertedSection> convertedSections = ucsbCurriculumService.getConvertedSections(subjectArea, quarterYYYYQ,
                "A");
            
            ctx.log("Found " + convertedSections.size() + " sections");
            ctx.log("Storing in MongoDB Collection...");
        
            int newSections = 0;
            int updatedSections = 0;
            int errors = 0;
        
            for (ConvertedSection section : convertedSections) {
                try {
                    String quarter = section.getCourseInfo().getQuarter();
                    String enrollCode =  section.getSection().getEnrollCode();
                    Optional<ConvertedSection> optionalSection = convertedSectionCollection
                                .findOneByQuarterAndEnrollCode(quarter,enrollCode);
                    if (optionalSection.isPresent()) {
                        ConvertedSection existingSection = optionalSection.get();
                        existingSection.setCourseInfo(section.getCourseInfo());
                        existingSection.setSection(section.getSection());
                        convertedSectionCollection.save(existingSection);
                        updatedSections++;
                    } else {
                        convertedSectionCollection.save(section);
                        newSections++;
                    }
                } catch (Exception e) {
                    ctx.log("Error saving section: " + e.getMessage());
                    errors++;
                }
            }
            
            ctx.log(String.format("%d new sections saved, %d sections updated, %d errors", newSections, updatedSections,
                        errors));
            ctx.log("Courses for [" + subjectArea + " " + quarterYYYYQ + "] have been updated");
        }

        ctx.log("Quarter courses for [" + quarterYYYYQ + "] have been updated");

    }
}