import controller.ConsoleManager;
import controller.Library;
import model.User;
//todo email не отправляется, если хотя бы одна почта указан неверно
//todo filemanager упростить код, связанный с шифровкой пароля(млишком много конвертации)
//todo проверка вводимых значений, особенно bookType
//todo постраничный просмотр каталога?
public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        library.downloadData();
        ConsoleManager console = new ConsoleManager();
        User user = null;
        int answer;
        while(true) {
            if (user == null) {
                console.printStartMenu();
                answer = console.getNumber();
                switch (answer) {
                    case 1:
                        user = library.logIn();
                        break;
                    case 2:
                        library.register();
                        break;
                    case 3:
                        System.exit(0);
                    default:
                        System.out.println("Такого варианта нет, введите заново\n");
                }
            }

            while ((user != null) && (user instanceof model.Admin)) {
                console.printAdminMenu();
                answer = console.getNumber();
                switch (answer) {
                    case 1:
                        library.showCatalog();
                        break;
                    case 2:
                        library.addBook();
                        break;
                    case 3:
                        library.deleteBook();
                        break;
                    case 4:
                        user = library.logOut();
                        break;
                    default:
                        System.out.println("Такого варианта нет, введите заново\n");
                }

            }
            while ((user != null) && (user instanceof model.RegularUser)) {
                console.printUserMenu();
                answer = console.getNumber();
                switch (answer) {
                    case 1:
                        library.showCatalog();
                        break;
                    case 2:
                        library.findBook();
                        break;
                    case 3:
                        library.offerBook();
                        break;
                    case 4:
                        user = library.logOut();
                        break;
                    default:
                        System.out.println("Такого варианта нет, введите заново\n");
                }
            }
        }
    }
}