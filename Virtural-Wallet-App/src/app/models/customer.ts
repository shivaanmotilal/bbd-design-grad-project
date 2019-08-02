import { Account } from './account';

export class Customer {
    constructor(
        public customerId: string,
        public firstName: string,
        public surname:string,
        public email: string,
        public phoneNumber: string,
        public accounts: Account[]
    ) { }
}
