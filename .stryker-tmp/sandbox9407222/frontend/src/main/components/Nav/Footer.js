import { Container } from "react-bootstrap";

export const space = " ";

export default function Footer(systemInfo) {

  return (
    <footer className="bg-light pt-3 pt-md-4 pb-4 pb-md-5">
      <Container>
        <p>
          This app is a class project of{space}
          <a
            data-testid="footer-class-website-link"
            href="https://ucsb-cs156.github.io"
            target="_blank"
            rel="noopener noreferrer"
          >
            CMPSC 156
          </a>
          {space}
          at
          {space}
          <a data-testid="footer-ucsb-link" href="https://ucsb.edu" target="_blank" rel="noopener noreferrer">
            UCSB
          </a>
          . Check out the source code on
          {space}

          {systemInfo.systemInfo && (
            <a
              data-testid="footer-source-code-link"
              href={systemInfo.systemInfo.sourceRepo}
              target="_blank"
              rel="noopener noreferrer"
            >
              GitHub
            </a>
          )}

          {!systemInfo.systemInfo && (
            <a
              data-testid="footer-source-code-link"
              href={"https://github.com/ucsb-cs156-f22/f22-5pm-courses"}
              target="_blank"
              rel="noopener noreferrer"
            >
              GitHub
            </a>
          )}
        

          !
          This is not an official source of UCSB course information. An official
          source can be found
          {space}
          <a
            data-testid="footer-course-search-link"
            href="https://my.sa.ucsb.edu/public/curriculum/coursesearch.aspx"
            target="_blank"
            rel="noopener noreferrer"
          >
            here
          </a>
          .
        </p>
        <p>
          The cartoon Storke Tower images in the brand logo and favicon for this site were
          developed by Chelsea Lyon-Hayden, Art Director for UCSB Associate Students, and are
          used here by permission of the Executive Director of UCSB Associated Students.
          These images are Copyright © 2021 UCSB Associated Students, and may not be reused
          without express written permission of the Executive Director of UCSB Associated Students.  For more info, visit:
          {space}
          <a data-testid="footer-sticker-link" href="https://www.as.ucsb.edu/sticker-packs">www.as.ucsb.edu/sticker-packs/</a>
        </p>
      </Container>
    </footer>
  );
}
