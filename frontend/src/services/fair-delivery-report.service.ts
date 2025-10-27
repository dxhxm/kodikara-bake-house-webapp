import apiClient from '@/lib/api-client';

export interface FairDeliveryReportDTO {
  reportId: string;
  reportDate?: string;
  reportMonth?: string;
  reportType: 'DAILY' | 'MONTHLY';
  totalDeliveries: number;
  totalRevenue: number;
  totalProfit: number;
  totalExpenses: number;
  generatedOn: string;
}

export const fairDeliveryReportService = {
  // Generate daily report
  generateDaily: async (date: string): Promise<FairDeliveryReportDTO> => {
    const response = await apiClient.post<FairDeliveryReportDTO>(`/fair-delivery-reports/daily?date=${date}`);
    return response.data;
  },

  // Generate monthly report
  generateMonthly: async (month: string): Promise<FairDeliveryReportDTO> => {
    const response = await apiClient.post<FairDeliveryReportDTO>(`/fair-delivery-reports/monthly?month=${month}`);
    return response.data;
  },

  // Get all reports
  list: async (): Promise<FairDeliveryReportDTO[]> => {
    const response = await apiClient.get<FairDeliveryReportDTO[]>('/fair-delivery-reports');
    return response.data;
  },

  // Get reports by date
  getByDate: async (date: string): Promise<FairDeliveryReportDTO[]> => {
    const response = await apiClient.get<FairDeliveryReportDTO[]>(`/fair-delivery-reports/by-date?date=${date}`);
    return response.data;
  },

  // Get reports by month
  getByMonth: async (month: string): Promise<FairDeliveryReportDTO[]> => {
    const response = await apiClient.get<FairDeliveryReportDTO[]>(`/fair-delivery-reports/by-month?month=${month}`);
    return response.data;
  },

  // Get single report
  get: async (id: string): Promise<FairDeliveryReportDTO> => {
    const response = await apiClient.get<FairDeliveryReportDTO>(`/fair-delivery-reports/${id}`);
    return response.data;
  },

  // Delete report
  delete: async (id: string): Promise<void> => {
    await apiClient.delete(`/fair-delivery-reports/${id}`);
  },
};

