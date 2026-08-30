package online_food_order_system.online_food_order_system.models;

import jakarta.persistence.*;


    @Entity
    @Table(name = "login_data")
    public class Login {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        private String email;
        private String password;
        private String usertype;


        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }


        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }



        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }



        public String getUsertype() {
            return usertype;
        }

        public void setUsertype(String usertype) {
            this.usertype = usertype;
        }
    }

