import { Transaction } from './transaction';

export class Account {
    constructor(
        public accountNumber: string,
        public balance: number,
        public availableBalance: number,
        public closed: number,
        public transactions: Transaction[]
    ) { }
}
