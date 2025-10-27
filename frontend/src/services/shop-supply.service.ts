import apiClient from '@/lib/api-client';

export interface ShopSupplyItemDTO {
  productId: string;
  productName: string;
  quantity: number;
  price: number;
}

export interface ShopSupplyRequestDTO {
  shopId: string;
  driverId: string;
  items: ShopSupplyItemDTO[];
}

export interface ShopSupplyResponseDTO {
  supplyId: string;
  supplyDate: string;
  driverId: string;
  driverName: string;
  shopId: string;
  shopName: string;
  totalAmount: number;
  items: ShopSupplyItemDTO[];
}

export const shopSupplyService = {
  create: async (supply: ShopSupplyRequestDTO): Promise<ShopSupplyResponseDTO> => {
    const response = await apiClient.post<ShopSupplyResponseDTO>('/shop-supplies', supply);
    return response.data;
  },

  get: async (id: string): Promise<ShopSupplyResponseDTO> => {
    const response = await apiClient.get<ShopSupplyResponseDTO>(`/shop-supplies/${id}`);
    return response.data;
  },

  list: async (): Promise<ShopSupplyResponseDTO[]> => {
    const response = await apiClient.get<ShopSupplyResponseDTO[]>('/shop-supplies');
    return response.data;
  },

  update: async (id: string, supply: ShopSupplyRequestDTO): Promise<ShopSupplyResponseDTO> => {
    const response = await apiClient.put<ShopSupplyResponseDTO>(`/shop-supplies/${id}`, supply);
    return response.data;
  },

  delete: async (id: string): Promise<void> => {
    await apiClient.delete(`/shop-supplies/${id}`);
  },
};

