import http from '../api/http';

const ShopService = {
  getAllShops: async () => {
    try {
      const response = await http.get('/api/shops'); // Assuming /api/shops endpoint exists
      return response.data;
    } catch (error) {
      console.error("Error fetching shops:", error);
      throw error;
    }
  },
};

export default ShopService;
