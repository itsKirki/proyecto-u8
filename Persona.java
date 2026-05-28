
    public class Persona {
        protected int id;
        protected String nombre;
        protected int telefono;
        protected String email;

        public Persona() {
            this.id = 0;
            this.nombre = "Default";
            this.telefono = 0;
            this.email = "default@example.com";
        }

        public Persona(int id, String nombre, int telefono, String email) {
            this.id = id;
            this.nombre = nombre;
            this.telefono = telefono;
            this.email = email;
        }

        public int getId() {
            return this.id;
        }

        public void setId(int id) {
            this.id = id >= 0 ? id : 0;
        }

        public String getNombre() {
            return this.nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public int getTelefono() {
            return this.telefono;
        }

        public void setTelefono(int telefono) {
            this.telefono = telefono >= 0 ? telefono : 0;
        }

        public String getEmail() {
            return this.email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }