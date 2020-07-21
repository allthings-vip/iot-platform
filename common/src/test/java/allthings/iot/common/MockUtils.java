//package iot.tf56.common;
//
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//
//import java.util.List;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
///**
// * Created by sylar on 16/5/8.
// */
//public class MockUtils {
//
//    public static void doGe(MockMvc mockMvc, String url, List<String> paramKeys, List<String[]> paramValues) throws
// Exception {
//        MockHttpServletRequestBuilder builder = get(url);
//
//        if (paramKeys != null && paramValues != null && paramKeys.size() > 0) {
//            for (int i = 0; i < Math.min(paramKeys.size(), paramValues.size()); i++) {
//                builder = builder.param(paramKeys.get(i), paramValues.get(i));
//            }
//        }
//
//        mockMvc.perform(builder
//                .characterEncoding("UTF-8")
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    public static void doPost(MockMvc mockMvc, String url, String json) throws Exception {
//        mockMvc.perform(post(url, "json")
//                .characterEncoding("UTF-8")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(json.getBytes()))
//                .andDo(print());
//    }
//}
