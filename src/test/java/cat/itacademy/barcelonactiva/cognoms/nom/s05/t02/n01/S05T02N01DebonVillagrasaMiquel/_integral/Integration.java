package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel._integral;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class Integration {

    @Autowired
    MockMvc mockMvc;

    /**
     * {
     *     "firstname" : "Silvia",
     *     "lastname" : "Garcia",
     *     "email" : "silviagarcia@admin.com",
     *     "password" : "passwordSil45+”
     * }
     */


    @DisplayName("Test register without a body JSONUser user then return Ok")
    @Test
    public void testRegisterReturnInternalServerError()throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/mysql/auth/register")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @DisplayName("Test register with an invalid URL then return Forbidden")
    @Test
    public void testRegisterReturnForbidden()throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/_api/mysql/auth/register")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @DisplayName("Test login with a registered user then return Ok")
    @Test
    public void testLoginReturnOk()throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/mysql/auth/authenticate")
                    .content("{\"email\":\"silviagarcia@admin.com\",\"password\":\"passwordSil45+\"}")
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @DisplayName("Test login with a not registered user then return NotFound")
    @Test
    public void testLoginReturnNotFound()throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/mysql/auth/authenticate")
                    .content("{\"email\":\"silviagardecia@admin.com\",\"password\":\"passwordSil45+dede\"}")
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }

    @DisplayName("Test Register but the JSON is invalid then return BadRequest")
    @Test
    public void testRegisterReturnBadRequest()throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/mysql/auth/register")
                        .content("{\"email\":\"silviagarcia@admin.com\",\"password\":\"passwordSil45+\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Test Register with a valid JSON then return Ok")
    @Test
    public void testRegisterValidJsonReturnOK()throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/mysql/auth/register")
                        .content("{\"firstname\":\"testMiquel\",\"lastname\":\"testLastName\",\"email\":\"miqueltest@admin.com\",\"password\":\"pasEswordl45+\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @DisplayName("Test Register with a invalid Password then return BadRequest")
    @Test
    public void testRegisterValidJsonInvalidPasswordReturnError()throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/mysql/auth/register")
                        .content("{\"firstname\":\"testMiquel\",\"lastname\":\"testLastName\",\"email\":\"miqueltest@admin.com\",\"password\":\"prdl45+\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDiceGameReturnOk () throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/mysql/auth/authenticate")
                        .content("{\"email\":\"silviagarcia@admin.com\",\"password\":\"passwordSil45+\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andReturn();
        String token = result.getResponse().getContentAsString().substring(10, result.getResponse().getContentAsString().length() - 2);
        System.out.println(token);

        mockMvc.perform(MockMvcRequestBuilders.get("/players")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(greaterThan(-1)))
                .andDo(print());
    }





}
