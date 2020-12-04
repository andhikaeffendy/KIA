package com.kominfo.anaksehat.helpers.apihelper;

import java.util.Date;

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

//    @GET("states")
//    Call<ResponseBody> getStates(@Query("auth_token") String token);
//
//    @GET("states/{id}")
//    Call<ResponseBody> getState(@Query("auth_token") String token,
//                                @Path("id") long state_id);

    @GET("districts")
    Call<ResponseBody> getDistrics(@Query("auth_token") String token);
                                  // @Query("state_id") long state_id);

    @GET("districts/{id}")
    Call<ResponseBody> getDistrict(@Path("id") long district_id,
                                   @Query("auth_token") String token);

    @GET("sub_districts")
    Call<ResponseBody> getSubDistricts(@Query("auth_token") String auth_token);

    @GET("sub_districts/{id}")
    Call<ResponseBody> getSubDistrict(@Path("id") long sub_district_id,
                                      @Query("auth_token") String auth_token);

    @GET("villages")
    Call<ResponseBody> getVillages(@Query("sub_district_id") long sub_district_id,
                                   @Query("auth_token") String auth_token);

    @GET("villages/{id}")
    Call<ResponseBody> getVillage(@Path("id") long village_id,
                                  @Query("auth_token") String auth_token);

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
                                   @Part("give_birth_id") RequestBody give_birth_id,
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
                                   @Field("mother_id") long mother_id,
                                   @Field("give_birth_id") long give_birth_id,
                                   @Field("child_number") int child_number,
                                   @Field("jampersal_status") String jampersal_status,
                                   @Field("note") String note,
                                   @Field("nb_baby_conditions_ids") String nb_baby_conditions_ids,
                                   @Field("nb_baby_treatments_ids") String nb_baby_treatments_ids);

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
                                   @Field("mother_id") long mother_id,
                                   @Field("child_number") int child_number,
                                   @Field("jampersal_status") String jampersal_status,
                                   @Field("note") String note,
                                   @Field("nb_baby_conditions_ids") String nb_baby_conditions_ids,
                                   @Field("nb_baby_treatments_ids") String nb_baby_treatments_ids);

    @GET("mothers")
    Call<ResponseBody> getMothers(@Query("auth_token") String token);

    @GET("mothers/{id}")
    Call<ResponseBody> getMother(@Path("id") long id,
                                 @Query("auth_token") String token);

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
                                    @Field("kk_name") RequestBody kk_name,
                                    @Field("nik") RequestBody nik,
                                    @Field("jampersal_status") RequestBody jampersal_status,
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
                                   // @Part("state_id") RequestBody state_id,
                                    @Part("district_id") RequestBody district_id,
                                    @Part("height") RequestBody height,
                                    @Part("weight") RequestBody weight,
                                    @Part("blood_pressure_top") RequestBody blood_pressure_top,
                                    @Part("blood_pressure_bottom") RequestBody blood_pressure_bottom,
                                    @Part("kk_name") RequestBody kk_name,
                                    @Part("nik") RequestBody nik,
                                    @Part("jampersal_status") RequestBody jampersal_status,
                                    @Part("sub_district_id") RequestBody sub_district_id,
                                    @Part("village_id") RequestBody village_id,
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
                                    //@Field("state_id") long state_id,
                                    @Field("district_id") long district_id,
                                    @Field("height") int height,
                                    @Field("weight") double weight,
                                    @Field("blood_pressure_top") int blood_pressure_top,
                                    @Field("blood_pressure_bottom") int blood_pressure_bottom,
                                    @Field("kk_name") String kk_name,
                                    @Field("nik") String nik,
                                    @Field("jampersal_status") String jampersal_status,
                                    @Field("sub_district_id") long sub_district_id,
                                    @Field("village_id") long village_id,
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
                                       @Field("mother_id") long mother_id,
                                       @Field("arm_round") int arm_round,
                                       @Field("kek_status") int kek_status,
                                       @Field("kontrasepsi") String kontrasepsi,
                                       @Field("disease_histories") String disease_histories,
                                       @Field("allergy_histories") String allergy_histories,
                                       @Field("pregnancy_number") int pregnancy_number,
                                       @Field("birth_count") int birth_count,
                                       @Field("miscarriage_count") int miscarriage_count,
                                       @Field("g_count") int g_count,
                                       @Field("p_count") int p_count,
                                       @Field("a_count") int a_count,
                                       @Field("children_count") int children_count,
                                       @Field("dead_birth_count") int dead_birth_count,
                                       @Field("premature_children_count") int premature_children_count,
                                       @Field("distance") String distance,
                                       @Field("immunization_status") String immunization_status,
                                       @Field("last_birth_helper") String last_birth_helper,
                                       @Field("last_birth_way") String last_birth_way);

    @FormUrlEncoded
    @POST("pregnancies")
    Call<ResponseBody> createPregnancy(@Field("auth_token") String token,
                                       @Field("period_type") int period_type,
                                       @Field("last_period_date") String last_period_date,
                                       @Field("start_weight") double start_weight,
                                       @Field("mother_id") long mother_id,
                                       @Field("arm_round") int arm_round,
                                       @Field("kek_status") int kek_status,
                                       @Field("kontrasepsi") String kontrasepsi,
                                       @Field("disease_histories") String disease_histories,
                                       @Field("allergy_histories") String allergy_histories,
                                       @Field("pregnancy_number") int pregnancy_number,
                                       @Field("birth_count") int birth_count,
                                       @Field("miscarriage_count") int miscarriage_count,
                                       @Field("g_count") int g_count,
                                       @Field("p_count") int p_count,
                                       @Field("a_count") int a_count,
                                       @Field("children_count") int children_count,
                                       @Field("dead_birth_count") int dead_birth_count,
                                       @Field("premature_children_count") int premature_children_count,
                                       @Field("distance") String distance,
                                       @Field("immunization_status") String immunization_status,
                                       @Field("last_birth_helper") String last_birth_helper,
                                       @Field("last_birth_way") String last_birth_way);

    @FormUrlEncoded
    @PUT("pregnancies/{id}")
    Call<ResponseBody> updatePregnancy(@Path("id") long id,
                                       @Field("id") long pregnancy_id,
                                       @Field("auth_token") String token,
                                       @Field("last_period_date") String last_period_date,
                                       @Field("start_height") int start_height,
                                       @Field("start_weight") double start_weight,
                                       @Field("period_type") int period_type,
                                       @Field("mother_id") long mother_id,
                                       @Field("arm_round") int arm_round,
                                       @Field("kek_status") int kek_status,
                                       @Field("kontrasepsi") String kontrasepsi,
                                       @Field("disease_histories") String disease_histories,
                                       @Field("allergy_histories") String allergy_histories,
                                       @Field("pregnancy_number") int pregnancy_number,
                                       @Field("birth_count") int birth_count,
                                       @Field("miscarriage_count") int miscarriage_count,
                                       @Field("g_count") int g_count,
                                       @Field("p_count") int p_count,
                                       @Field("a_count") int a_count,
                                       @Field("children_count") int children_count,
                                       @Field("dead_birth_count") int dead_birth_count,
                                       @Field("premature_children_count") int premature_children_count,
                                       @Field("distance") String distance,
                                       @Field("immunization_status") String immunization_status,
                                       @Field("last_birth_helper") String last_birth_helper,
                                       @Field("last_birth_way") String last_birth_way);

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


    @GET("baby_histories")
    Call<ResponseBody> getListPemeriksaanBayi(@Query("auth_token") String token,
                                          @Query("child_id") long id);

    @GET("baby_histories/{id}")
    Call<ResponseBody> getPemeriksaanBayi(@Path("id") long id,
                                          @Query("auth_token") String token);

    @GET("public_health_centers/pregnancies")
    Call<ResponseBody> getPemetaanIbuHamil();

    @GET("public_health_centers/children")
    Call<ResponseBody> getPemetaanAnak();

    @GET("infographics/summary")
    Call<ResponseBody> getSummary();

    @FormUrlEncoded
    @POST("baby_histories")
    Call<ResponseBody> createPemeriksaanBayi(@Field("auth_token") String token,
                                             @Field("child_id") long child_id,
                                             @Field("history_date") String date,
                                             @Field("weight") double weight,
                                             @Field("height") int height,
                                             @Field("temperature") double temp,
                                             @Field("respiratory") int respiratory,
                                             @Field("heart_beat") int heart_beat,
                                             @Field("infection") String infection,
                                             @Field("ikterus") String ikterus,
                                             @Field("diare") String diare,
                                             @Field("low_weight") String low_weight,
                                             @Field("k_vitamin") String k_vitamin,
                                             @Field("hb_bcg_polio") String hb_bcg_polio,
                                             @Field("shk") String shk,
                                             @Field("shk_confirmation") String shk_confirmation,
                                             @Field("treatment") String treatment,
                                             @Field("user_id") long user_id);

    @FormUrlEncoded
    @PUT("baby_histories/{id}")
    Call<ResponseBody> updatePemeriksaanBayi(@Path("id") long id,
                                             @Field("child_id") long child_id,
                                             @Field("auth_token") String token,
                                             @Field("history_date") String date,
                                             @Field("weight") double weight,
                                             @Field("height") int height,
                                             @Field("temperature") double temp,
                                             @Field("respiratory") int respiratory,
                                             @Field("heart_beat") int heart_beat,
                                             @Field("infection") String infection,
                                             @Field("ikterus") String ikterus,
                                             @Field("diare") String diare,
                                             @Field("low_weight") String low_weight,
                                             @Field("k_vitamin") String k_vitamin,
                                             @Field("hb_bcg_polio") String hb_bcg_polio,
                                             @Field("shk") String shk,
                                             @Field("shk_confirmation") String shk_confirmation,
                                             @Field("treatment") String treatment,
                                             @Field("user_id") long user_id);

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
                                              @Field("blood_pressure_top") int blood_pressure_top,
                                              @Field("blood_pressure_bottom") int blood_pressure_bottom,
                                              @Field("baby_weight") double baby_weight,
                                              @Field("gender_prediction") String gender_prediction,
                                              @Field("amniotic_condition") int amniotic_condition,
                                              @Field("note") String note,
                                              @Field("fundus_height") int fundus_height,
                                              @Field("fetus_position") String fetus_position,
                                              @Field("heart_beat") int heart_beat,
                                              @Field("leg") String leg,
                                              @Field("lab") String lab,
                                              @Field("treatment") String treatment,
                                              @Field("suggestion") String suggestion,
                                              @Field("next_visit_date") String next_visit_date,
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
                                              @Field("blood_pressure_top") int blood_pressure_top,
                                              @Field("blood_pressure_bottom") int blood_pressure_bottom,
                                              @Field("baby_weight") double baby_weight,
                                              @Field("gender_prediction") String gender_prediction,
                                              @Field("amniotic_condition") int amniotic_condition,
                                              @Field("note") String note,
                                              @Field("fundus_height") int fundus_height,
                                              @Field("fetus_position") String fetus_position,
                                              @Field("heart_beat") int heart_beat,
                                              @Field("leg") String leg,
                                              @Field("lab") String lab,
                                              @Field("treatment") String treatment,
                                              @Field("suggestion") String suggestion,
                                              @Field("next_visit_date") String next_visit_date,
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

    @GET("nb_baby_conditions")
    Call<ResponseBody> getBabyConditions(@Query("auth_token") String token);

    @GET("nb_baby_treatments")
    Call<ResponseBody> getBabyTreatment(@Query("auth_token") String token);

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

    @GET("give_births/{id}")
    Call<ResponseBody> getGiveBirth(@Path("id") long id,
                                    @Query("auth_token") String token);

    @FormUrlEncoded
    @POST("give_births")
    Call<ResponseBody> createGiveBirth(@Field("auth_token") String token,
                                       @Field("pregnancy_id") long pregnancy_id,
                                       @Field("mother_id") long mother_id,
                                       @Field("birth_date") String birth_date,
                                       @Field("birth_time") String birth_time,
                                       @Field("pregnancy_age") int pregnancy_age,
                                       @Field("bitrh_helper") String bitrh_helper,
                                       @Field("birth_way_id") String birth_way_id,
                                       @Field("mother_condition_id") long mother_condition_id,
                                       @Field("remarks") String remarks
    );

    @FormUrlEncoded
    @PUT("give_births/{id}")
    Call<ResponseBody> updateGiveBirth(@Path("id") long id,
                                       @Field("id") long give_birth_id,
                                       @Field("auth_token") String token,
                                       @Field("pregnancy_id") long pregnancy_id,
                                       @Field("mother_id") long mother_id,
                                       @Field("birth_date") String birth_date,
                                       @Field("birth_time") String birth_time,
                                       @Field("pregnancy_age") int pregnancy_age,
                                       @Field("bitrh_helper") String bitrh_helper,
                                       @Field("birth_way_id") String birth_way_id,
                                       @Field("mother_condition_id") long mother_condition_id,
                                       @Field("remarks") String remarks);

    @GET("birth_ways")
    Call<ResponseBody> getBirthWays();

    @GET("mother_conditions")
    Call<ResponseBody> getMotherConditions();

    @GET("neonatal_visit_types")
    Call<ResponseBody> getNeonatalVisitTypes();

    @GET("nifas_history_types")
    Call<ResponseBody> getNifasHistoryTypes();

    @GET("nifas_histories")
    Call<ResponseBody> getNifasHistories(@Query("auth_token") String token,
                                         @Query("give_birth_id") long give_birth_id);

    @GET("nifas_histories/{id}")
    Call<ResponseBody> getNifasHistory(@Path("id") long id,
                                       @Query("auth_token") String token);

    @FormUrlEncoded
    @POST("nifas_histories")
    Call<ResponseBody> createNifasHistory(@Field("auth_token") String token,
                                          @Field("give_birth_id") long give_birth_id,
                                          @Field("history_date") String history_date,
                                          @Field("nifas_history_type_id") long nifas_history_type_id,
                                          @Field("mother_condition") String mother_condition,
                                          @Field("blood_temp_respiration") String blood_temp_respiration,
                                          @Field("blooding_pervaginam") String blooding_pervaginam,
                                          @Field("perineum") String perineum,
                                          @Field("infection") String infection,
                                          @Field("uteri") String uteri,
                                          @Field("fundus_uteri") String fundus_uteri,
                                          @Field("lokhia") String lokhia,
                                          @Field("birth_canal") String birth_canal,
                                          @Field("breast") String breast,
                                          @Field("asi") String asi,
                                          @Field("vitamin_a") String vitamin_a,
                                          @Field("kontrasepsi") String kontrasepsi,
                                          @Field("high_risk") String high_risk,
                                          @Field("bab") String bab,
                                          @Field("bak") String bak,
                                          @Field("good_food") String good_food,
                                          @Field("drink") String drink,
                                          @Field("cleanliness") String cleanliness,
                                          @Field("take_a_rest") String take_a_rest,
                                          @Field("caesar_take_care") String caesar_take_care,
                                          @Field("breastfeeding") String breastfeeding,
                                          @Field("baby_treatment") String baby_treatment,
                                          @Field("baby_cry") String baby_cry,
                                          @Field("baby_communication") String baby_communication,
                                          @Field("kb_consultation") String kb_consultation
    );

    @FormUrlEncoded
    @PUT("nifas_histories/{id}")
    Call<ResponseBody> updateNifasHistory(@Path("id") long id,
                                          @Field("id") long nifas_history_id,
                                          @Field("auth_token") String token,
                                          @Field("give_birth_id") long give_birth_id,
                                          @Field("history_date") String history_date,
                                          @Field("nifas_history_type_id") long nifas_history_type_id,
                                          @Field("mother_condition") String mother_condition,
                                          @Field("blood_temp_respiration") String blood_temp_respiration,
                                          @Field("blooding_pervaginam") String blooding_pervaginam,
                                          @Field("perineum") String perineum,
                                          @Field("infection") String infection,
                                          @Field("uteri") String uteri,
                                          @Field("fundus_uteri") String fundus_uteri,
                                          @Field("lokhia") String lokhia,
                                          @Field("birth_canal") String birth_canal,
                                          @Field("breast") String breast,
                                          @Field("asi") String asi,
                                          @Field("vitamin_a") String vitamin_a,
                                          @Field("kontrasepsi") String kontrasepsi,
                                          @Field("high_risk") String high_risk,
                                          @Field("bab") String bab,
                                          @Field("bak") String bak,
                                          @Field("good_food") String good_food,
                                          @Field("drink") String drink,
                                          @Field("cleanliness") String cleanliness,
                                          @Field("take_a_rest") String take_a_rest,
                                          @Field("caesar_take_care") String caesar_take_care,
                                          @Field("breastfeeding") String breastfeeding,
                                          @Field("baby_treatment") String baby_treatment,
                                          @Field("baby_cry") String baby_cry,
                                          @Field("baby_communication") String baby_communication,
                                          @Field("kb_consultation") String kb_consultation
    );

    @GET("birth_plannings")
    Call<ResponseBody> getBirthPlannings(@Query("auth_token") String token,
                                         @Query("pregnancy_id") long pregnancy_id);

    @GET("birth_plannings/{id}")
    Call<ResponseBody> getBirthPlanning(@Path("id") long id,
                                        @Query("auth_token") String token);

    @FormUrlEncoded
    @POST("birth_plannings")
    Call<ResponseBody> createBirthPlanning(@Field("auth_token") String token,
                                           @Field("pregnancy_id") long pregnancy_id,
                                           @Field("mother_id") long mother_id,
                                           @Field("meeting_date") String meeting_date,
                                           @Field("birth_planning_date") String birth_planning_date,
                                           @Field("location") String location,
                                           @Field("birth_planning_place") String birth_planning_place,
                                           @Field("birth_helper_mother") String birth_helper_mother,
                                           @Field("birth_helper_family") String birth_helper_family,
                                           @Field("birth_notice") String birth_notice,
                                           @Field("danger_notice") String danger_notice,
                                           @Field("transportation_problem") String transportation_problem,
                                           @Field("mother_keeper") String mother_keeper,
                                           @Field("cost_problem") String cost_problem,
                                           @Field("blood_giver") String blood_giver,
                                           @Field("birth_partner") String birth_partner,
                                           @Field("children_keeper") String children_keeper,
                                           @Field("kb_method") String kb_method,
                                           @Field("helper_discussion") String helper_discussion,
                                           @Field("home_condition") String home_condition,
                                           @Field("home_equipment") String home_equipment
    );

    @FormUrlEncoded
    @PUT("birth_plannings/{id}")
    Call<ResponseBody> updateBirthPlanning(@Path("id") long id,
                                           @Field("id") long birth_planning_id,
                                           @Field("auth_token") String token,
                                           @Field("pregnancy_id") long pregnancy_id,
                                           @Field("mother_id") long mother_id,
                                           @Field("meeting_date") String meeting_date,
                                           @Field("birth_planning_date") String birth_planning_date,
                                           @Field("location") String location,
                                           @Field("birth_planning_place") String birth_planning_place,
                                           @Field("birth_helper_mother") String birth_helper_mother,
                                           @Field("birth_helper_family") String birth_helper_family,
                                           @Field("birth_notice") String birth_notice,
                                           @Field("danger_notice") String danger_notice,
                                           @Field("transportation_problem") String transportation_problem,
                                           @Field("mother_keeper") String mother_keeper,
                                           @Field("cost_problem") String cost_problem,
                                           @Field("blood_giver") String blood_giver,
                                           @Field("birth_partner") String birth_partner,
                                           @Field("children_keeper") String children_keeper,
                                           @Field("kb_method") String kb_method,
                                           @Field("helper_discussion") String helper_discussion,
                                           @Field("home_condition") String home_condition,
                                           @Field("home_equipment") String home_equipment
    );

    @GET("neonatal_histories")
    Call<ResponseBody> getNeonatals(@Query("auth_token") String token,
                                    @Query("child_id") long pregnancy_id);

    @GET("neonatal_histories/{id}")
    Call<ResponseBody> getNeonatal(@Path("id") long id,
                                   @Query("auth_token") String token);

    @FormUrlEncoded
    @POST("neonatal_histories")
    Call<ResponseBody> createNeonatal(@Field("auth_token") String token,
                                      @Field("child_id") long child_id,
                                      @Field("neonatal_visit_type_id") long neonatal_visit_type_id,
                                      @Field("history_date") String history_date,
                                      @Field("weight") double weight,
                                      @Field("height") int height,
                                      @Field("temperature") double temperature,
                                      @Field("respiratory") String respiratory,
                                      @Field("heart_beat") String heart_beat,
                                      @Field("infection") String infection,
                                      @Field("ikterus") String ikterus,
                                      @Field("diare") String diare,
                                      @Field("low_weight") String low_weight,
                                      @Field("k_vitamin") String k_vitamin,
                                      @Field("hb_bcg_polio") String hb_bcg_polio,
                                      @Field("shk") String shk,
                                      @Field("shk_confirmation") String shk_confirmation,
                                      @Field("treatment") String treatment
    );

    @FormUrlEncoded
    @PUT("neonatal_histories/{id}")
    Call<ResponseBody> updateNeonatal(@Path("id") long id,
                                      @Field("id") long neonatal_id,
                                      @Field("auth_token") String token,
                                      @Field("child_id") long child_id,
                                      @Field("neonatal_visit_type_id") long neonatal_visit_type_id,
                                      @Field("history_date") String history_date,
                                      @Field("weight") double weight,
                                      @Field("height") int height,
                                      @Field("temperature") double temperature,
                                      @Field("respiratory") String respiratory,
                                      @Field("heart_beat") String heart_beat,
                                      @Field("infection") String infection,
                                      @Field("ikterus") String ikterus,
                                      @Field("diare") String diare,
                                      @Field("low_weight") String low_weight,
                                      @Field("k_vitamin") String k_vitamin,
                                      @Field("hb_bcg_polio") String hb_bcg_polio,
                                      @Field("shk") String shk,
                                      @Field("shk_confirmation") String shk_confirmation,
                                      @Field("treatment") String treatment
    );

}
