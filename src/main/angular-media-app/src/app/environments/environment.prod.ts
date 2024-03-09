import { UserService as MockUserService } from "../services/mocks/user.mock.service";
import { UserService } from "../services/user.service";

export const environment = {
    production: true,
    providers: [
        { provide: UserService, useClass: MockUserService}
    ]
};