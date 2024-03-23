import { UserService as MockUserService } from "../auth/services/mocks/user.mock.service";
import { UserService } from "../auth/services/user.service";

export const environment = {
    production: true,
    providers: [
        { provide: UserService, useClass: MockUserService}
    ]
};