import apiClient from '@/lib/api-client';

export interface ShopSupplyReportDTO {
  reportId?: string;
  reportDate?: string; // YYYY-MM-DD
  reportMonth?: string; // YYYY-MM
  reportType: 'DAILY' | 'MONTHLY';
  totalSupplies?: number;
  totalAmount?: number;
  totalShopsServed?: number;
  generatedOn?: string;
}

export const shopSupplyReportService = {
  /**
   * Generate a daily shop supply report
   */
  generateDaily: async (date: string): Promise<ShopSupplyReportDTO> => {
    const response = await apiClient.post<ShopSupplyReportDTO>(
      `/shop-supply-reports/daily?date=${date}`
    );
    return response.data;
  },

  /**
   * Generate a monthly shop supply report
   */
  generateMonthly: async (month: string): Promise<ShopSupplyReportDTO> => {
    const response = await apiClient.post<ShopSupplyReportDTO>(
      `/shop-supply-reports/monthly?month=${month}`
    );
    return response.data;
  },

  /**
   * Get all shop supply reports
   */
  list: async (): Promise<ShopSupplyReportDTO[]> => {
    const response = await apiClient.get<ShopSupplyReportDTO[]>('/shop-supply-reports');
    return response.data;
  },

  /**
   * Get reports by date
   */
  getByDate: async (date: string): Promise<ShopSupplyReportDTO[]> => {
    const response = await apiClient.get<ShopSupplyReportDTO[]>(
      `/shop-supply-reports/by-date?date=${date}`
    );
    return response.data;
  },

  /**
   * Get reports by month
   */
  getByMonth: async (month: string): Promise<ShopSupplyReportDTO[]> => {
    const response = await apiClient.get<ShopSupplyReportDTO[]>(
      `/shop-supply-reports/by-month?month=${month}`
    );
    return response.data;
  },

  /**
   * Get a specific report by ID
   */
  get: async (id: string): Promise<ShopSupplyReportDTO> => {
    const response = await apiClient.get<ShopSupplyReportDTO>(`/shop-supply-reports/${id}`);
    return response.data;
  },

  /**
   * Delete a report
   */
  delete: async (id: string): Promise<void> => {
    await apiClient.delete(`/shop-supply-reports/${id}`);
  },
};

