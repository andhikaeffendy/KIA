package com.kominfo.anaksehat.helpers.apihelper;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by emrekabir on 2/08/2018.
 */

public interface BaseApiService {

    @FormUrlEncoded
    @POST("users/sign_in")
    Call<ResponseBody> loginRequest(@Field("username") String username,
                                    @Field("password") String password);

    @FormUrlEncoded
    @POST("users/sign_out")
    Call<ResponseBody> logoutRequest(@Field("auth_token") String token);

//    @FormUrlEncoded
//    @POST("users")
//    Call<ResponseBody> signinRequest(@Field("username") String username,
//                                     @Field("name") String name,
//                                     @Field("email") String email,
//                                     @Field("phone_number") String phone_number,
//                                     @Field("posyandu") int posyandu,
//                                     @Field("password") String password,
//                                     @Field("password_confirmation") String confirmPassword,
//                                     @Field("state_id") long state_id,
//                                     @Field("district_id") long district_id);

    @FormUrlEncoded
    @POST("users")
    Call<ResponseBody> signinRequest(@Field("email") String email,
                                     @Field("phone_number") String phone_number,
                                     @Field("posyandu") int posyandu,
                                     @Field("password") String password,
                                     @Field("password_confirmation") String confirmPassword);



    @Multipart
    @POST("users")
    Call<ResponseBody> signinRequest(
//            @Part("username") RequestBody username,
//                                     @Part("name") RequestBody name,
                                     @Part("email") RequestBody email,
                                     @Part("phone_number") RequestBody phone_number,
                                     @Part("posyandu") RequestBody posyandu,
                                     @Part("password") RequestBody password);
//                                     @Part("password_confirmation") RequestBody confirmPassword,
//                                     @Part("state_id") RequestBody state_id,
//                                     @Part("district_id") RequestBody district_id);
                                     //@Part MultipartBody.Part legal_file);

    @FormUrlEncoded
    @POST("users/activate_account")
    Call<ResponseBody> activation(@Field("phone_number") String phone_number,
                                  @Field("activation_number") String activation_number);

    @FormUrlEncoded
    @POST("users/resend_activation_code")
    Call<ResponseBody> resendCode(@Field("phone_number") String phone_number);

    @FormUrlEncoded
    @POST("users/forgot_password")
    Call<ResponseBody> forgotPassword(@Field("phone_number") String phone_number);

    @GET("states")
    Call<ResponseBody> getStates(@Query("auth_token") String token);

    @GET("states/{id}")
    Call<ResponseBody> getState(@Query("auth_token") String token,
                                @Path("id") long state_id);

    @GET("districts")
    Call<ResponseBody> getDistrics(@Query("auth_token") String token,
                                   @Query("state_id") long state_id);

    @GET("districts/{id}")
    Call<ResponseBody> getDistrict(@Query("auth_token") String token,
                                   @Path("id") long district_id);

    @GET("children")
    Call<ResponseBody> getChildren(@Query("auth_token") String token);

    @GET("children")
    Call<ResponseBody> getChildren(@Query("auth_token") String token,
                                   @Query("mother_id") long mother_id);

    @GET("children/{id}")
    Call<ResponseBody> getChild(@Path("id") long id,
                                @Query("auth_token") String token);

    @GET("children/get_last_child")
    Call<ResponseBody> getLastChild(@Query("auth_token") String token,
                                    @Query("mother_id") long id);

    @Multipart
    @POST("children")
    Call<ResponseBody> createChild(@Part("auth_token") RequestBody token,
                                   @Part("birth_date") RequestBody birth_date,
                                   @Part("name") RequestBody name,
                                   @Part("gender") RequestBody gender,
                                   @Part("blood_type") RequestBody blood_type,
                                   @Part("first_length") RequestBody first_length,
                                   @Part("first_weight") RequestBody first_weight,
                                   @Part("first_head_round") RequestBody first_head_round,
                                   @Part("mother_id") RequestBody mother_id,
                                   @Part MultipartBody.Part photo);

    @Multipart
    @PUT("children/{id}")
    Call<ResponseBody> updateChild(@Path("id") long id,
                                   @Part("id") RequestBody child_id,
                                   @Part("auth_token") RequestBody token,
                                   @Part("birth_date") RequestBody birth_date,
                                   @Part("name") RequestBody name,
                                   @Part("gender") RequestBody gender,
                                   @Part("blood_type") RequestBody blood_type,
                                   @Part("first_length") RequestBody first_length,
                                   @Part("first_weight") RequestBody first_weight,
                                   @Part("first_head_round") RequestBody first_head_round,
                                   @Part("mother_id") RequestBody mother_id,
                                   @Part MultipartBody.Part photo);

    @FormUrlEncoded
    @POST("children")
    Call<ResponseBody> createChild(@Field("auth_token") String token,
                                   @Field("birth_date") String birth_date,
                                   @Field("name") String name,
                                   @Field("gender") String gender,
                                   @Field("blood_type") String blood_type,
                                   @Field("first_length") int first_length,
                                   @Field("first_weight") double first_weight,
                                   @Field("first_head_round") int first_head_round,
                                   @Field("mother_id") long mother_id);

    @FormUrlEncoded
    @PUT("children/{id}")
    Call<ResponseBody> updateChild(@Path("id") long id,
                                   @Field("id") long child_id,
                                   @Field("auth_token") String token,
                                   @Field("birth_date") String birth_date,
                                   @Field("name") String name,
                                   @Field("gender") String gender,
                                   @Field("blood_type") String blood_type,
                                   @Field("first_length") int first_length,
                                   @Field("first_weight") double first_weight,
                                   @Field("first_head_round") int first_head_round,
                                   @Field("mother_id") long mother_id);

    @GET("mothers")
    Call<ResponseBody> getMothers(@Query("auth_token") String token);

    @GET("mothers/{id}")
    Call<ResponseBody> getMother(@Path("id") long id,
                                 @Query("auth_token") String token);

    @GET("sub_districts")
    Call<ResponseBody> createSubDistrict(@Query("auth_token") String auth_token);

    @GET("sub_districts/{id}")
    Call<ResponseBody> getSubDistrict(@Field("auth_token") String auth_token,
                                      @Path("id") int id);

//    @Multipart
//    @POST("mothers")
//    Call<ResponseBody> createMother(@Part("auth_token") RequestBody token,
//                                    @Part("birth_date") RequestBody birth_date,
//                                    @Part("name") RequestBody name,
//                                    @Part("blood_type") RequestBody blood_type,
//                                    @Part("spouse_name") RequestBody spouse_name,
//                                    @Part("address") RequestBody address,
//                                    @Part("state_id") RequestBody state_id,
//                                    @Part("district_id") RequestBody district_id,
//                                    @Part("height") RequestBody height,
//                                    @Part("weight") RequestBody weight,
//                                    @Part("blood_pressure_top") RequestBody blood_pressure_top,
//                                    @Part("blood_pressure_bottom") RequestBody blood_pressure_bottom,
//                                    @Part MultipartBody.Part photo);



    @Multipart
    @POST("mothers")
    Call<ResponseBody> createMother(@Part("auth_token") RequestBody token,
                                    @Part("name") RequestBody name,
                                    @Field("birth_date") RequestBody birth_date,
                                    @Part("height") RequestBody height,
                                    @Part("weight") RequestBody weight,
                                    @Part("district_id") RequestBody district_id,
                                    @Field("kk_name") String kk_name,
                                    @Field("nik") String nik,
                                    @Field("jampersal_status") String jampersal_status,
                                    @Part MultipartBody.Part photo);

    @Multipart
    @PUT("mothers/{id}")
    Call<ResponseBody> updateMother(@Path("id") long id,
                                    @Part("id") RequestBody mother_id,
                                    @Part("auth_token") RequestBody token,
                                    @Part("birth_date") RequestBody birth_date,
                                    @Part("name") RequestBody name,
                                    @Part("blood_type") RequestBody blood_type,
                                    @Part("spouse_name") RequestBody spouse_name,
                                    @Part("state_id") RequestBody state_id,
                                    @Part("district_id") RequestBody district_id,
                                    @Part("height") RequestBody height,
                                    @Part("weight") RequestBody weight,
                                    @Part("blood_pressure_top") RequestBody blood_pressure_top,
                                    @Part("blood_pressure_bottom") RequestBody blood_pressure_bottom,
                                    @Field("kk_name") String kk_name,
                                    @Field("nik") String nik,
                                    @Field("jampersal_status") String jampersal_status,
                                    @Field("sub_district_id") int sub_district_id,
                                    @Field("village_id") int village_id,
                                    @Part("address") RequestBody address,
                                    @Part MultipartBody.Part photo);

//    @FormUrlEncoded
//    @POST("mothers")
//    Call<ResponseBody> createMother(@Field("auth_token") String token,
//                                    @Field("birth_date") String birth_date,
//                                    @Field("name") String name,
//                                    @Field("blood_type") String blood_type,
//                                    @Field("spouse_name") String spouse_name,
//                                    @Field("address") String address,
//                                    @Field("state_id") long state_id,
//                                    @Field("district_id") long district_id,
//                                    @Field("height") int height,
//                                    @Field("weight") double weight,
//                                    @Field("blood_pressure_top") int blood_pressure_top,
//                                    @Field("blood_pressure_bottom") int blood_pressure_bottom);

    @FormUrlEncoded
    @POST("mothers")
    Call<ResponseBody> createMother(@Field("auth_token") String token,
                                    @Field("name") String name,
                                    @Field("birth_date") String birth_date,
                                    @Field("height") int height,
                                    @Field("district_id") int district_id,
                                    @Field("weight") double weight,
                                    @Field("kk_name") String kk_name,
                                    @Field("nik") String nik,
                                    @Field("jampersal_status") String jampersal_status);

    @FormUrlEncoded
    @PUT("mothers/{id}")
    Call<ResponseBody> updateMother(@Path("id") long id,
                                    @Field("id") long mother_id,
                                    @Field("auth_token") String token,
                                    @Field("birth_date") String birth_date,
                                    @Field("name") String name,
                                    @Field("blood_type") String blood_type,
                                    @Field("spouse_name") String spouse_name,
                                    @Field("state_id") long state_id,
                                    @Field("district_id") long district_id,
                                    @Field("height") int height,
                                    @Field("weight") double weight,
                                    @Field("blood_pressure_top") int blood_pressure_top,
                                    @Field("blood_pressure_bottom") int blood_pressure_bottom,
                                    @Field("kk_name") String kk_name,
                                    @Field("nik") String nik,
                                    @Field("jampersal_status") String jampersal_status,
                                    @Field("sub_district_id") int sub_district_id,
                                    @Field("village_id") int village_id,
                                    @Field("address") String address);

    @GET("child_histories")
    Call<ResponseBody> getChildHistories(@Query("auth_token") String token);

    @GET("child_histories")
    Call<ResponseBody> getChildHistories(@Query("auth_token") String token,
                                         @Query("child_id") long id);

    @GET("child_histories/{id}")
    Call<ResponseBody> getChildHistory(@Path("id") long id,
                                       @Query("auth_token") String token);

    @FormUrlEncoded
    @POST("child_histories")
    Call<ResponseBody> createChildHistory(@Field("auth_token") String token,
                                          @Field("history_date") String history_date,
                                          @Field("height") int height,
                                          @Field("weight") double weight,
                                          @Field("head_round") int head_round,
                                          @Field("child_id") long child_id,
                                          @Field("temperature") int temperature,
                                          @Field("note") String note);

    @FormUrlEncoded
    @POST("child_histories")
    Call<ResponseBody> createChildHistory(@Field("auth_token") String token,
                                          @Field("history_date") String history_date,
                                          @Field("height") int height,
                                          @Field("weight") double weight,
                                          @Field("child_id") long child_id);

    @FormUrlEncoded
    @PUT("child_histories/{id}")
    Call<ResponseBody> updateChildHistory(@Path("id") long id,
                                          @Field("id") long child_history_id,
                                          @Field("auth_token") String token,
                                          @Field("history_date") String history_date,
                                          @Field("height") int height,
                                          @Field("weight") double weight,
                                          @Field("head_round") int head_round,
                                          @Field("child_id") long child_id,
                                          @Field("temperature") int temperature,
                                          @Field("note") String note);

    @FormUrlEncoded
    @PUT("child_histories/{id}")
    Call<ResponseBody> updateChildHistory(@Path("id") long id,
                                          @Field("id") long child_history_id,
                                          @Field("auth_token") String token,
                                          @Field("history_date") String history_date,
                                          @Field("height") int height,
                                          @Field("weight") double weight,
                                          @Field("child_id") long child_id);

    @GET("pregnancies")
    Call<ResponseBody> getPregnancies(@Query("auth_token") String token);

    @GET("pregnancies")
    Call<ResponseBody> getPregnancies(@Query("auth_token") String token,
                                      @Query("mother_id") long mother_id);

    @GET("pregnancies/{id}")
    Call<ResponseBody> getPregnancy(@Path("id") long id,
                                    @Query("auth_token") String token);

    @GET("pregnancies/get_last_pregnancy")
    Call<ResponseBody> getLastPregnancy(@Query("auth_token") String token,
                                        @Query("mother_id") long id);

    @FormUrlEncoded
    @POST("pregnancies")
    Call<ResponseBody> createPregnancy(@Field("auth_token") String token,
                                       @Field("last_period_date") String last_period_date,
                                       @Field("start_height") int start_height,
                                       @Field("start_weight") double start_weight,
                                       @Field("period_type") int period_type,
                                       @Field("mother_id") long mother_id);

//    @Field("arm_round") int arm_round,
//    @Field("kek_status") int kek_status,
//    @Field("kontrasepsi") String kontrasepsi,
//    @Field("kontrasepsi") String disease_histories,
//    @Field("allergy_histories") String allergy_histories,
//    @Field("pregnancy_number") int pregnancy_number,
//    @Field("birth_count") int birth_count,
//    @Field("miscarriage_count") int miscarriage_count,
//    @Field("g_count") int g_count,
//    @Field("p_count") int p_count,
//    @Field("a_count") int a_count,
//    @Field("children_count") int children_count,
//    @Field("dead_birth_count") int dead_birth_count,
//    @Field("premature_children_count") int premature_children_count,
//    @Field("distance") String distance,
//    @Field("mmunization_status") String mmunization_status,
//    @Field("last_birth_helper") String last_birth_helper,
//    @Field("last_birth_way") String last_birth_way

    @FormUrlEncoded
    @POST("pregnancies")
    Call<ResponseBody> createPregnancy(@Field("auth_token") String token,
                                       @Field("period_type") int period_type,
                                       @Field("last_period_date") String last_period_date,
                                       @Field("start_weight") double start_weight,
                                       @Field("mother_id") long mother_id);

    @FormUrlEncoded
    @PUT("pregnancies/{id}")
    Call<ResponseBody> updatePregnancy(@Path("id") long id,
                                       @Field("id") long pregnancy_id,
                                       @Field("auth_token") String token,
                                       @Field("last_period_date") String last_period_date,
                                       @Field("start_height") int start_height,
                                       @Field("start_weight") double start_weight,
                                       @Field("period_type") int period_type,
                                       @Field("mother_id") long mother_id);

//    @Field("arm_round") int arm_round,
//    @Field("kek_status") int kek_status,
//    @Field("kontrasepsi") String kontrasepsi,
//    @Field("kontrasepsi") String disease_histories,
//    @Field("allergy_histories") String allergy_histories,
//    @Field("pregnancy_number") int pregnancy_number,
//    @Field("birth_count") int birth_count,
//    @Field("miscarriage_count") int miscarriage_count,
//    @Field("g_count") int g_count,
//    @Field("p_count") int p_count,
//    @Field("a_count") int a_count,
//    @Field("children_count") int children_count,
//    @Field("dead_birth_count") int dead_birth_count,
//    @Field("premature_children_count") int premature_children_count,
//    @Field("distance") String distance,
//    @Field("mmunization_status") String mmunization_status,
//    @Field("last_birth_helper") String last_birth_helper,
//    @Field("last_birth_way") String last_birth_way

    @FormUrlEncoded
    @PUT("pregnancies/{id}")
    Call<ResponseBody> updatePregnancy(@Path("id") long id,
                                       @Field("id") long pregnancy_id,
                                       @Field("auth_token") String token,
                                       @Field("period_type") int period_type,
                                       @Field("last_period_date") String last_period_date,
                                       @Field("start_weight") double start_weight,
                                       @Field("mother_id") long mother_id);

    @GET("pregnancy_histories")
    Call<ResponseBody> getPregnancyHistories(@Query("auth_token") String token);

    @GET("pregnancy_histories")
    Call<ResponseBody> getPregnancyHistories(@Query("auth_token") String token,
                                             @Query("pregnancy_id") long id);

    @GET("pregnancy_histories/{id}")
    Call<ResponseBody> getPregnancyHistory(@Path("id") long id,
                                           @Query("auth_token") String token);

    @FormUrlEncoded
    @POST("pregnancy_histories")
    Call<ResponseBody> createPregnancyHistory(@Field("auth_token") String token,
                                              @Field("history_date") String history_date,
                                              @Field("weight") double weight,
                                              @Field("blood_pressure_top") int blood_pressure_top,
                                              @Field("blood_pressure_bottom") int blood_pressure_bottom,
                                              @Field("baby_weight") double baby_weight,
                                              @Field("gender_prediction") String gender_prediction,
                                              @Field("amniotic_condition") int amniotic_condition,
                                              @Field("note") String note,
                                              @Field("pregnancy_id") long pregnancy_id);

    @FormUrlEncoded
    @POST("pregnancy_histories")
    Call<ResponseBody> createPregnancyHistory(@Field("auth_token") String token,
                                              @Field("history_date") String history_date,
                                              @Field("weight") double weight,
                                              @Field("gender_prediction") String gender_prediction,
                                              @Field("pregnancy_id") long pregnancy_id);

    @FormUrlEncoded
    @PUT("pregnancy_histories/{id}")
    Call<ResponseBody> updatePregnancyHistory(@Path("id") long id,
                                              @Field("id") long pregnancy_history_id,
                                              @Field("auth_token") String token,
                                              @Field("history_date") String history_date,
                                              @Field("weight") double weight,
                                              @Field("blood_pressure_top") int blood_pressure_top,
                                              @Field("blood_pressure_bottom") int blood_pressure_bottom,
                                              @Field("baby_weight") double baby_weight,
                                              @Field("gender_prediction") String gender_prediction,
                                              @Field("amniotic_condition") int amniotic_condition,
                                              @Field("note") String note,
                                              @Field("pregnancy_id") long pregnancy_id);

    @FormUrlEncoded
    @PUT("pregnancy_histories/{id}")
    Call<ResponseBody> updatePregnancyHistory(@Path("id") long id,
                                              @Field("id") long pregnancy_history_id,
                                              @Field("auth_token") String token,
                                              @Field("history_date") String history_date,
                                              @Field("weight") double weight,
                                              @Field("gender_prediction") String gender_prediction,
                                              @Field("pregnancy_id") long pregnancy_id);

    @GET("articles")
    Call<ResponseBody> getArticles();

    @GET("articles/{id}")
    Call<ResponseBody> getArticle(@Path("id") long id);

    @GET("immunizations")
    Call<ResponseBody> getImmunizations(@Query("auth_token") String token);

    @GET("immunizations")
    Call<ResponseBody> getImmunizations(@Query("auth_token") String token,
                                        @Query("child_id") long id);

    @FormUrlEncoded
    @POST("immunizations")
    Call<ResponseBody> createImmunization(@Field("auth_token") String token,
                                          @Field("immunization_date") String immunization_date,
                                          @Field("vaccine_id") long vaccine_id,
                                          @Field("child_id") long child_id);

    @FormUrlEncoded
    @PUT("immunizations/{id}")
    Call<ResponseBody> updateImmunization(@Path("id") long id,
                                          @Field("id") long immunization_id,
                                          @Field("auth_token") String token,
                                          @Field("immunization_date") String immunization_date);

    @GET("vaccines")
    Call<ResponseBody> getVaccines(@Query("auth_token") String token);

    @GET("infographics")
    Call<ResponseBody> getInfographics();

    @GET("infographics/{id}")
    Call<ResponseBody> getInfographic(@Path("id") long id);

    @GET("videos")
    Call<ResponseBody> getVideos();

    @GET("videos/{id}")
    Call<ResponseBody> getVideo(@Path("id") long id);

    @GET("contents")
    Call<ResponseBody> getContents();

}
