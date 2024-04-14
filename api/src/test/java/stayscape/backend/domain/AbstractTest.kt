package stayscape.backend.domain

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase

@SpringBootTestWithProfiles
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
abstract class AbstractTest{}
