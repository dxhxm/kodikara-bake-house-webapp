import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { ArrowLeft, Plus, Trash2, Edit2 } from "lucide-react";
import { useToast } from "@/hooks/use-toast";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { shopSupplyService, ShopSupplyRequestDTO, ShopSupplyItemDTO, ShopSupplyResponseDTO } from "@/services/shop-supply.service";
import { productService, ProductDTO } from "@/services/product.service";
import { shopService, ShopDTO } from "@/services/shop.service";
import { driverService, DriverDTO } from "@/services/driver.service";

interface ProductItem {
  id: string;
  productId: string;
  productName: string;
  quantity: number;
  price: number;
}

const ShopDelivery = () => {
  const navigate = useNavigate();
  const { toast } = useToast();
  
  const [shopId, setShopId] = useState("");
  const [driverId, setDriverId] = useState("");
  const [products, setProducts] = useState<ProductItem[]>([
    { id: "1", productId: "", productName: "", quantity: 0, price: 0 }
  ]);
  const [availableProducts, setAvailableProducts] = useState<ProductDTO[]>([]);
  const [availableShops, setAvailableShops] = useState<ShopDTO[]>([]);
  const [availableDrivers, setAvailableDrivers] = useState<DriverDTO[]>([]);
  const [savedDeliveries, setSavedDeliveries] = useState<ShopSupplyResponseDTO[]>([]);
  const [editingDeliveryId, setEditingDeliveryId] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    loadAllData();
  }, []);

  const loadAllData = async () => {
    await Promise.all([
      loadProducts(),
      loadShops(),
      loadDrivers(),
      loadDeliveries()
    ]);
  };

  const loadProducts = async () => {
    try {
      const data = await productService.list();
      setAvailableProducts(data);
    } catch (error: any) {
      console.error("Failed to load products:", error);
    }
  };

  const loadShops = async () => {
    try {
      const data = await shopService.list();
      setAvailableShops(data);
    } catch (error: any) {
      console.error("Failed to load shops:", error);
    }
  };

  const loadDrivers = async () => {
    try {
      const data = await driverService.list();
      setAvailableDrivers(data);
    } catch (error: any) {
      console.error("Failed to load drivers:", error);
    }
  };

  const loadDeliveries = async () => {
    try {
      const data = await shopSupplyService.list();
      setSavedDeliveries(data);
    } catch (error: any) {
      console.error("Failed to load deliveries:", error);
    }
  };

  const addProduct = () => {
    const newProduct: ProductItem = {
      id: Date.now().toString(),
      productId: "",
      productName: "",
      quantity: 0,
      price: 0,
    };
    setProducts([...products, newProduct]);
  };

  const updateProduct = (id: string, field: keyof ProductItem, value: any) => {
    setProducts(products.map(product => {
      if (product.id === id) {
        if (field === 'productId' && typeof value === 'string') {
          // When product is selected, auto-fill name and price
          const selectedProduct = availableProducts.find(p => p.proId === value);
          if (selectedProduct) {
            return {
              ...product,
              productId: value,
              productName: selectedProduct.name,
              price: selectedProduct.unitPrice,
            };
          }
        }
        return { ...product, [field]: value };
      }
      return product;
    }));
  };

  const removeProduct = (id: string) => {
    if (products.length > 1) {
      setProducts(products.filter(product => product.id !== id));
    }
  };

  const handleSave = async () => {
    if (!shopId || !driverId) {
      toast({
        title: "Error",
        description: "Please select shop and driver",
        variant: "destructive",
      });
      return;
    }

    const hasIncompleteProduct = products.some(p => !p.productId || p.quantity <= 0 || p.price <= 0);
    if (hasIncompleteProduct) {
      toast({
        title: "Error",
        description: "Please complete all product details with valid values",
        variant: "destructive",
      });
      return;
    }

    setIsLoading(true);
    try {
      const items: ShopSupplyItemDTO[] = products.map(p => ({
        productId: p.productId,
        productName: p.productName,
        quantity: p.quantity,
        price: p.price,
      }));

      const requestBody: ShopSupplyRequestDTO = {
        shopId,
        driverId,
        items,
      };

      if (editingDeliveryId) {
        // Update existing delivery
        await shopSupplyService.update(editingDeliveryId, requestBody);
        toast({
          title: "Success",
          description: "Shop delivery updated successfully",
        });
      } else {
        // Create new delivery
        await shopSupplyService.create(requestBody);
        toast({
          title: "Success",
          description: "Shop delivery saved successfully",
        });
      }

      // Reload deliveries
      await loadDeliveries();

      // Reset form
      resetForm();
    } catch (error: any) {
      toast({
        title: "Error",
        description: error.response?.data?.message || "Failed to save shop delivery. Please try again.",
        variant: "destructive",
      });
    } finally {
      setIsLoading(false);
    }
  };

  const handleEdit = (delivery: ShopSupplyResponseDTO) => {
    // Load delivery data into form
    setEditingDeliveryId(delivery.supplyId);
    setShopId(delivery.shopId || "");
    setDriverId(delivery.driverId || "");

    // Load products
    if (delivery.items && delivery.items.length > 0) {
      const loadedProducts: ProductItem[] = delivery.items.map((item, index) => {
        // Find the product to get its ID
        const product = availableProducts.find(p => p.name === item.productName);
        return {
          id: index.toString(),
          productId: product?.proId || "",
          productName: item.productName,
          quantity: item.quantity,
          price: item.price,
        };
      });
      setProducts(loadedProducts);
    }

    // Scroll to top to show form
    window.scrollTo({ top: 0, behavior: 'smooth' });

    toast({
      title: "Edit Mode",
      description: "Update the delivery details and click Save",
    });
  };

  const handleDelete = async (deliveryId: string) => {
    if (!confirm("Are you sure you want to delete this delivery? This action cannot be undone.")) {
      return;
    }

    setIsLoading(true);
    try {
      await shopSupplyService.delete(deliveryId);
      toast({
        title: "Success",
        description: "Shop delivery deleted successfully",
      });

      // Reload deliveries
      await loadDeliveries();
    } catch (error: any) {
      console.error("Error deleting delivery:", error);
      const errorMessage = error.response?.data?.message || error.response?.data || error.message || "Failed to delete delivery";
      toast({
        title: "Error",
        description: String(errorMessage),
        variant: "destructive",
      });
    } finally {
      setIsLoading(false);
    }
  };

  const resetForm = () => {
    setEditingDeliveryId(null);
    setShopId("");
    setDriverId("");
    setProducts([{ id: "1", productId: "", productName: "", quantity: 0, price: 0 }]);
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-bakery-cream to-bakery-warm">
      <header className="bg-white/80 backdrop-blur-sm border-b border-border shadow-[var(--shadow-soft)]">
        <div className="container mx-auto px-4 py-4 flex items-center justify-between">
          <div className="flex items-center space-x-3">
            <Button variant="ghost" onClick={() => navigate("/dashboard")}>
              <ArrowLeft className="h-4 w-4" />
            </Button>
            <h1 className="text-xl font-bold text-bakery-brown">Deliver to Shops</h1>
          </div>
        </div>
      </header>

      <main className="container mx-auto px-4 py-8 space-y-8">
        {/* Form Card */}
        <div className="flex justify-center">
          <Card className="w-full max-w-2xl">
            <CardHeader>
              <CardTitle>{editingDeliveryId ? "Edit Shop Delivery" : "New Shop Delivery"}</CardTitle>
            </CardHeader>
            <CardContent className="space-y-4">
              {/* Shop Selection & Driver Info */}
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                  <Label htmlFor="shop">Select Shop * ({availableShops.length} available)</Label>
                  <Select value={shopId} onValueChange={setShopId} disabled={isLoading}>
                    <SelectTrigger>
                      <SelectValue placeholder="Select shop" />
                    </SelectTrigger>
                    <SelectContent>
                      {availableShops.map((shop) => (
                        <SelectItem key={shop.shopId} value={shop.shopId!}>
                          {shop.shopName} - {shop.ownerName}
                        </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                </div>
                <div>
                  <Label htmlFor="driver">Select Driver * ({availableDrivers.length} available)</Label>
                  <Select value={driverId} onValueChange={setDriverId} disabled={isLoading}>
                    <SelectTrigger>
                      <SelectValue placeholder="Select driver" />
                    </SelectTrigger>
                    <SelectContent>
                      {availableDrivers.map((driver) => (
                        <SelectItem key={driver.driverId} value={driver.driverId}>
                          {driver.driverName}
                        </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                </div>
              </div>

            {/* Products Section */}
            <div className="space-y-3 mt-6">
              <div className="flex items-center justify-between">
                <Label className="text-base font-semibold">Products</Label>
                <Button onClick={addProduct} size="sm" variant="outline" disabled={isLoading}>
                  <Plus className="h-4 w-4 mr-1" />
                  Add Product
                </Button>
              </div>

              <div className="space-y-2">
                {products.map((product) => (
                  <div key={product.id} className="grid grid-cols-1 md:grid-cols-4 gap-2 p-3 border rounded-lg">
                    <div>
                      <Select
                        value={product.productId}
                        onValueChange={(value) => updateProduct(product.id, "productId", value)}
                        disabled={isLoading}
                      >
                        <SelectTrigger>
                          <SelectValue placeholder="Select product" />
                        </SelectTrigger>
                        <SelectContent>
                          {availableProducts.map((p) => (
                            <SelectItem key={p.proId} value={p.proId!}>
                              {p.name}
                            </SelectItem>
                          ))}
                        </SelectContent>
                      </Select>
                    </div>
                    <div>
                      <Input
                        type="number"
                        min="0"
                        value={product.quantity}
                        onChange={(e) => updateProduct(product.id, "quantity", Math.max(0, Number(e.target.value) || 0))}
                        placeholder="Quantity"
                        disabled={isLoading}
                      />
                    </div>
                    <div>
                      <Input
                        type="number"
                        min="0"
                        value={product.price}
                        onChange={(e) => updateProduct(product.id, "price", Math.max(0, Number(e.target.value) || 0))}
                        placeholder="Price"
                        disabled={isLoading}
                        readOnly
                      />
                    </div>
                    <div className="flex items-center space-x-2">
                      <Input
                        value={`Rs. ${(product.quantity * product.price).toFixed(2)}`}
                        disabled
                        className="bg-muted"
                      />
                      {products.length > 1 && (
                        <Button
                          variant="destructive"
                          size="sm"
                          onClick={() => removeProduct(product.id)}
                          disabled={isLoading}
                        >
                          <Trash2 className="h-4 w-4" />
                        </Button>
                      )}
                    </div>
                  </div>
                ))}
              </div>
            </div>

            {/* Total */}
            <div className="flex justify-end pt-4 border-t">
              <div className="text-lg font-semibold">
                Total: Rs. {products.reduce((sum, p) => sum + (p.quantity * p.price), 0).toFixed(2)}
              </div>
            </div>

            {/* Action Buttons */}
            <div className="flex justify-end space-x-2 mt-6 pt-4 border-t">
              <Button variant="outline" onClick={resetForm} disabled={isLoading}>
                {editingDeliveryId ? "Cancel Edit" : "Clear Form"}
              </Button>
              <Button onClick={handleSave} disabled={isLoading || availableShops.length === 0 || availableProducts.length === 0 || availableDrivers.length === 0}>
                {isLoading ? "Saving..." : editingDeliveryId ? "Update" : "Save"} Delivery
              </Button>
            </div>
          </CardContent>
        </Card>
        </div>

        {/* Saved Deliveries List */}
        {savedDeliveries.length > 0 && (
          <div className="space-y-4">
            <h2 className="text-xl font-semibold text-bakery-brown">Saved Deliveries ({savedDeliveries.length})</h2>
            {savedDeliveries.map((delivery) => (
              <Card key={delivery.supplyId} className="overflow-hidden">
                <div className="p-4 bg-gradient-to-r from-primary/5 to-accent/5">
                  <div className="flex justify-between items-start">
                    <div className="space-y-2 flex-1">
                      <div className="flex items-center gap-4">
                        <h3 className="font-semibold text-lg">{delivery.shopName}</h3>
                        <span className="px-3 py-1 rounded-full text-xs font-medium bg-blue-100 text-blue-700">
                          ID: {delivery.supplyId}
                        </span>
                      </div>
                      <p className="text-sm text-muted-foreground">Date: {delivery.supplyDate}</p>
                      <p className="text-sm text-muted-foreground">Driver: {delivery.driverName}</p>
                      
                      <div className="mt-3 space-y-2">
                        <p className="text-sm font-medium">Products:</p>
                        {delivery.items?.map((item, idx) => (
                          <div key={idx} className="pl-4 text-sm">
                            <p>
                              {item.productName} - 
                              Qty: {item.quantity}, 
                              Price: Rs. {item.price}, 
                              Total: Rs. {(item.quantity * item.price).toFixed(2)}
                            </p>
                          </div>
                        ))}
                      </div>

                      <div className="mt-3 pt-3 border-t">
                        <p className="text-lg font-bold text-green-600">
                          Total Amount: Rs. {Number(delivery.totalAmount).toFixed(2)}
                        </p>
                      </div>
                    </div>
                    
                    {/* Action Buttons */}
                    <div className="flex flex-col gap-2 ml-4">
                      <Button
                        size="sm"
                        variant="outline"
                        onClick={() => handleEdit(delivery)}
                        disabled={isLoading}
                        className="flex items-center gap-2"
                      >
                        <Edit2 className="h-4 w-4" />
                        Edit
                      </Button>
                      <Button
                        size="sm"
                        variant="destructive"
                        onClick={() => handleDelete(delivery.supplyId)}
                        disabled={isLoading}
                        className="flex items-center gap-2"
                      >
                        <Trash2 className="h-4 w-4" />
                        Delete
                      </Button>
                    </div>
                  </div>
                </div>
              </Card>
            ))}
          </div>
        )}
      </main>
    </div>
  );
};

export default ShopDelivery;
