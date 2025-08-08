import os

from PIL import Image

def map_coordinates(in1, in2, out):
    print(out)
    coord_map = Image.open(in1)
    target_img = Image.open(in2)
    img_out = coord_map.copy()
    target_pixels = target_img.load()
    pixels_out = img_out.load()
    for x in range(coord_map.size[0]):
        for y in range(coord_map.size[1]):
            coords = target_pixels[pixels_out[x, y][0]//2, pixels_out[x, y][1]//2]
            pixels_out[x, y] = (coords[0],coords[1],coords[2],coords[3])
    img_out.save(out)


if __name__ == '__main__':
    src_dir = "in"
    dst_dir = "out"
    for filename1 in os.listdir(src_dir):
        src_name1 = src_dir + '/' + filename1
        dst_name = dst_dir + '/' + filename1
        map_coordinates("map.png", src_name1, dst_name)
