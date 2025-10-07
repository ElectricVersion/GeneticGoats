import os

from PIL import Image

# Used for selectively replacing areas of edited textures with regenerated parts.

# Generate an image (out) that is a copy of dst image except for any pixels that are white in area_map.
# Those pixels will be replaced with their equivalent in src.
def copy_area(src, dst, area_map, out):
    img_src = Image.open(src)
    img_dst = Image.open(dst)
    img_map = Image.open(area_map)
    img_out = img_dst.copy()
    pixels_src = img_src.load()
    pixels_dst = img_dst.load()
    pixels_map = img_map.load()
    pixels_out = img_out.load()
    for x in range(img_map.size[0]):
        for y in range(img_map.size[1]):
            if pixels_map[x, y] == (255,255,255,255):
                pixels_out[x, y] = pixels_src[x, y]
    img_out.save(out)


if __name__ == '__main__':
    src_dir = "src_in" # The directory to copy certain pixels from
    dst_dir = "dst_in" # The directory to use as a base
    out_dir = "out"
    for filename in os.listdir(src_dir):
        src_name = src_dir + '/' + filename
        dst_name = dst_dir + '/' + filename
        area_name = "area_map.png"
        out_name = out_dir + '/' + filename
        copy_area(src_name, dst_name, area_name, out_name)