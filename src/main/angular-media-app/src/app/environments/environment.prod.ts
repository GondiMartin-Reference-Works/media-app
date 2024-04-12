
import { UserService } from "../services/user.service";

export const environment = {
    production: true,
    providers: [
        { provide: UserService}
    ]
};

export const baseUrl = "http://localhost:8080";