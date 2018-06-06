package com.telappoint.onlineresv.form;


/**
 * 
 * @author Murali
 * 
 */
public class LoginForm {
	
	private String account_number;
	private String first_name;
	private String last_name;
	private String middle_name;
	private String email;
	
	private String attrib1;
	private String attrib2;
	private String attrib3;
	private String attrib4;
	private String attrib5;
	private String attrib6;
	private String attrib7;
	private String attrib8;
	private String attrib9;
	private String attrib10;
	private String attrib11;
	private String attrib12;
	private String attrib13;
	private String attrib14;
	private String attrib15;
	private String attrib16;
	private String attrib17;
	private String attrib18;
	private String attrib19;
	private String attrib20;

	private String contact_phone;	
	private String contact_phone_1;// internal onlineresv
	private String contact_phone_2;// internal onlineresv
	private String contact_phone_3;// internal onlineresv	
	
	private String home_phone;
	private String home_phone_1;
	private String home_phone_2;
	private String home_phone_3;
	
	private String work_phone;
	private String work_phone_1;
	private String work_phone_2;
	private String work_phone_3;
	
	private String cell_phone;
	private String cell_phone_1;
	private String cell_phone_2;
	private String cell_phone_3;
	
	private String dob;
	private String sex;
	private String pin;

	private String remind_lang;
	private String remind_phone;
	private String remind_time;
	private String provider_ids;
	private String location_ids;
	private String address;
	private String address_2;
	private String city;
	private String state;
	private String zip_postal;

	private String display_company;
	private String display_procedure;
	private String display_location;
	private String display_department;
	private String display_event;
	private String date;
	private String display_time;
	private String display_seat;
	private String display_comment;
	
	private String comment;	
	
	private String clientCode;
	
	public String getDisplay_time() {
		return display_time;
	}

	public void setDisplay_time(String display_time) {
		this.display_time = display_time;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getContact_phone_1() {
		return contact_phone_1;
	}

	public void setContact_phone_1(String contact_phone_1) {
		this.contact_phone_1 = contact_phone_1;
	}

	public String getContact_phone_2() {
		return contact_phone_2;
	}

	public void setContact_phone_2(String contact_phone_2) {
		this.contact_phone_2 = contact_phone_2;
	}

	public String getContact_phone_3() {
		return contact_phone_3;
	}

	public void setContact_phone_3(String contact_phone_3) {
		this.contact_phone_3 = contact_phone_3;
	}

	public String getAttrib1() {
		return attrib1;
	}

	public void setAttrib1(String attrib1) {
		this.attrib1 = attrib1;
	}

	public String getAttrib2() {
		return attrib2;
	}

	public void setAttrib2(String attrib2) {
		this.attrib2 = attrib2;
	}

	public String getAttrib3() {
		return attrib3;
	}

	public void setAttrib3(String attrib3) {
		this.attrib3 = attrib3;
	}

	public String getAttrib4() {
		return attrib4;
	}

	public void setAttrib4(String attrib4) {
		this.attrib4 = attrib4;
	}

	public String getAttrib5() {
		return attrib5;
	}

	public void setAttrib5(String attrib5) {
		this.attrib5 = attrib5;
	}

	public String getAttrib6() {
		return attrib6;
	}

	public void setAttrib6(String attrib6) {
		this.attrib6 = attrib6;
	}

	public String getAttrib7() {
		return attrib7;
	}

	public void setAttrib7(String attrib7) {
		this.attrib7 = attrib7;
	}

	public String getAttrib8() {
		return attrib8;
	}

	public void setAttrib8(String attrib8) {
		this.attrib8 = attrib8;
	}

	public String getAttrib9() {
		return attrib9;
	}

	public void setAttrib9(String attrib9) {
		this.attrib9 = attrib9;
	}

	public String getAttrib10() {
		return attrib10;
	}

	public void setAttrib10(String attrib10) {
		this.attrib10 = attrib10;
	}

	public String getAttrib11() {
		return attrib11;
	}

	public void setAttrib11(String attrib11) {
		this.attrib11 = attrib11;
	}

	public String getAttrib12() {
		return attrib12;
	}

	public void setAttrib12(String attrib12) {
		this.attrib12 = attrib12;
	}

	public String getAttrib13() {
		return attrib13;
	}

	public void setAttrib13(String attrib13) {
		this.attrib13 = attrib13;
	}

	public String getAttrib14() {
		return attrib14;
	}

	public void setAttrib14(String attrib14) {
		this.attrib14 = attrib14;
	}

	public String getAttrib15() {
		return attrib15;
	}

	public void setAttrib15(String attrib15) {
		this.attrib15 = attrib15;
	}

	public String getAttrib16() {
		return attrib16;
	}

	public void setAttrib16(String attrib16) {
		this.attrib16 = attrib16;
	}

	public String getAttrib17() {
		return attrib17;
	}

	public void setAttrib17(String attrib17) {
		this.attrib17 = attrib17;
	}

	public String getAttrib18() {
		return attrib18;
	}

	public void setAttrib18(String attrib18) {
		this.attrib18 = attrib18;
	}

	public String getAttrib19() {
		return attrib19;
	}

	public void setAttrib19(String attrib19) {
		this.attrib19 = attrib19;
	}

	public String getAttrib20() {
		return attrib20;
	}

	public void setAttrib20(String attrib20) {
		this.attrib20 = attrib20;
	}

    public String getHome_phone() {
        return getHome_phone_1() == null ? home_phone : getHome_phone_1()+""+getHome_phone_2()+""+getHome_phone_3();
    }
    public void setHome_phone(String home_phone) {
        this.home_phone = home_phone;
    }
    public String getHome_phone_1() {
        return home_phone_1;
    }
    public void setHome_phone_1(String home_phone_1) {
        this.home_phone_1 = home_phone_1;
    }
    public String getHome_phone_2() {
        return home_phone_2;
    }
    public void setHome_phone_2(String home_phone_2) {
        this.home_phone_2 = home_phone_2;
    }
    public String getHome_phone_3() {
        return home_phone_3;
    }
    public void setHome_phone_3(String home_phone_3) {
        this.home_phone_3 = home_phone_3;
    }
    public String getWork_phone() {
		return getWork_phone_1() == null ? work_phone : getWork_phone_1()+""+getWork_phone_2()+""+getWork_phone_3() ;
	}

	public void setWork_phone(String work_phone) {
		this.work_phone = work_phone;
	}

    public String getWork_phone_1() {
        return work_phone_1;
    }
    public void setWork_phone_1(String work_phone_1) {
        this.work_phone_1 = work_phone_1;
    }
    public String getWork_phone_2() {
        return work_phone_2;
    }
    public void setWork_phone_2(String work_phone_2) {
        this.work_phone_2 = work_phone_2;
    }
    public String getWork_phone_3() {
        return work_phone_3;
    }
    public void setWork_phone_3(String work_phone_3) {
        this.work_phone_3 = work_phone_3;
    }
    public String getCell_phone() {
		return getCell_phone_1() == null ? cell_phone : getCell_phone_1()+""+getCell_phone_2() +""+getCell_phone_3();
	}

	public void setCell_phone(String cell_phone) {
		this.cell_phone = cell_phone;
	}
    public String getCell_phone_1() {
        return cell_phone_1;
    }
    public void setCell_phone_1(String cell_phone_1) {
        this.cell_phone_1 = cell_phone_1;
    }
    public String getCell_phone_2() {
        return cell_phone_2;
    }
    public void setCell_phone_2(String cell_phone_2) {
        this.cell_phone_2 = cell_phone_2;
    }
    public String getCell_phone_3() {
        return cell_phone_3;
    }
    public void setCell_phone_3(String cell_phone_3) {
        this.cell_phone_3 = cell_phone_3;
    }
    public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getRemind_lang() {
		return remind_lang;
	}

	public void setRemind_lang(String remind_lang) {
		this.remind_lang = remind_lang;
	}

	public String getRemind_phone() {
		return remind_phone;
	}

	public void setRemind_phone(String remind_phone) {
		this.remind_phone = remind_phone;
	}

	public String getRemind_time() {
		return remind_time;
	}

	public void setRemind_time(String remind_time) {
		this.remind_time = remind_time;
	}

	public String getProvider_ids() {
		return provider_ids;
	}

	public void setProvider_ids(String provider_ids) {
		this.provider_ids = provider_ids;
	}

	public String getLocation_ids() {
		return location_ids;
	}

	public void setLocation_ids(String location_ids) {
		this.location_ids = location_ids;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress_2() {
		return address_2;
	}

	public void setAddress_2(String address_2) {
		this.address_2 = address_2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip_postal() {
		return zip_postal;
	}

	public void setZip_postal(String zip_postal) {
		this.zip_postal = zip_postal;
	}
		
	public String getContact_phone() {
		return getContact_phone_1() == null ? contact_phone : getContact_phone_1() + "" + getContact_phone_2() + "" + getContact_phone_3();
	}

	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}

	public String getDisplay_department() {
		return display_department;
	}

	public void setDisplay_department(String display_department) {
		this.display_department = display_department;
	}

	public String getDisplay_location() {
		return display_location;
	}

	public void setDisplay_location(String display_location) {
		this.display_location = display_location;
	}

	public String getDisplay_procedure() {
		return display_procedure;
	}

	public void setDisplay_procedure(String display_procedure) {
		this.display_procedure = display_procedure;
	}
	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	
	public String getAccount_number() {
		return account_number;
	}

	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}

	public String getDisplay_company() {
		return display_company;
	}

	public void setDisplay_company(String display_company) {
		this.display_company = display_company;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDisplay_event() {
		return display_event;
	}

	public void setDisplay_event(String display_event) {
		this.display_event = display_event;
	}

	public String getDisplay_seat() {
		return display_seat;
	}

	public void setDisplay_seat(String display_seat) {
		this.display_seat = display_seat;
	}

	public String getDisplay_comment() {
		return display_comment;
	}

	public void setDisplay_comment(String display_comment) {
		this.display_comment = display_comment;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMiddle_name() {
		return middle_name;
	}

	public void setMiddle_name(String middle_name) {
		this.middle_name = middle_name;
	}
}