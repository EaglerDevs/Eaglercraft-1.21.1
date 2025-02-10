package net.minecraft.client.renderer.block.model;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import net.lax1dude.eaglercraft.v1_8.json.JSONTypeDeserializer;
import net.lax1dude.eaglercraft.v1_8.json.JSONTypeProvider;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemOverride {
    private final ResourceLocation location;
    private final Map<ResourceLocation, Float> mapResourceValues;

    public ItemOverride(ResourceLocation locationIn, Map<ResourceLocation, Float> p_i46571_2_) {
        this.location = locationIn;
        this.mapResourceValues = p_i46571_2_;
    }

    public ResourceLocation getLocation() {
        return this.location;
    }

    boolean matchesItemStack(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase livingEntity) {
        Item item = stack.getItem();

        for (Entry<ResourceLocation, Float> entry : this.mapResourceValues.entrySet()) {
            IItemPropertyGetter iitempropertygetter = item.getPropertyGetter((ResourceLocation) entry.getKey());

            if (iitempropertygetter == null || iitempropertygetter.apply(stack, worldIn,
                    livingEntity) < ((Float) entry.getValue()).floatValue()) {
                return false;
            }
        }

        return true;
    }

    public static ItemOverride deserialize(String json) {
        return (ItemOverride) JSONTypeProvider.deserialize(new JSONObject(json), ItemOverride.class);
    }

    public static class Deserializer implements JSONTypeDeserializer<JSONObject, ItemOverride> {
        public ItemOverride deserialize(JSONObject jsonObject) throws JSONException {
            ResourceLocation resourcelocation = new ResourceLocation(jsonObject.getString("model"));
            Map<ResourceLocation, Float> map = this.makeMapResourceValues(jsonObject);
            return new ItemOverride(resourcelocation, map);
        }

        protected Map<ResourceLocation, Float> makeMapResourceValues(JSONObject jsonObject) {
            Map<ResourceLocation, Float> map = Maps.<ResourceLocation, Float>newLinkedHashMap();
            JSONObject jsonPredicate = jsonObject.getJSONObject("predicate");

            for (String key : jsonPredicate.keySet()) {
                map.put(new ResourceLocation(key), Float.valueOf(jsonPredicate.getFloat(key)));
            }

            return map;
        }
    }
}
