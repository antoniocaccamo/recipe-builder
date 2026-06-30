from PIL import Image
import os


def main():
    print("Hello from py-imager!")
    src = "../logo.png"
    img = Image.open(src).convert("RGBA")
    os.makedirs("./outputs", exist_ok=True)

    # Save ICO with multiple sizes for broad browser support
    #sizes = [16, 32, 48, 64, 96, 128, 256]
    sizes = [16, 32, 48, 96, 128]
    icons = [img.resize((s, s), Image.LANCZOS) for s in sizes]

    icons[0].save(
        "./outputs/favicon.ico",
        format="ICO",
        sizes=[(s, s) for s in sizes],
        append_images=icons[1:]
    )

    # Save individual PNG favicons
    for size in sizes:
        img.resize((size, size), Image.LANCZOS).save(f"./outputs/favicon-{size}x{size}.png")

    # Apple touch icon
    img.resize((180, 180), Image.LANCZOS).save("./outputs/apple-touch-icon.png")

    print("All favicon files created.")


if __name__ == "__main__":
    main()











