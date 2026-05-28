
    public class Persona {
        protected int id;
        protected String nombre;
        protected int telefono;
        protected String email;

        public Persona(int id, String nombre, int telefono, String email) {
            this.id = id;
            this.nombre = nombre;
            this.telefono = telefono;
            this.email = email;
        }

        public String getNombre() {
            return nombre;
        }

        public int getTelefono() {
            return telefono;
        }

        public String getEmail() {
            return email;
        }
    }