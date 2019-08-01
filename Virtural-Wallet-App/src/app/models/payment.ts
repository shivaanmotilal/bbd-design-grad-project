export class Payment {
    constructor(
        public paymentId: string,
        public fromAccountNumber: string,
        public fromAccountOpeningBalance: number,
        public toAccountNumber: string,
        public toAccountOpeningBalance: number,
        public amount: number,
        public dateInitiation: string,
        public dateSettlement: string,
        public settled: number
    ) { }
}
